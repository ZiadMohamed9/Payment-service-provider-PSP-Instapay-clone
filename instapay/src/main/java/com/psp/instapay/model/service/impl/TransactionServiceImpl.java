package com.psp.instapay.model.service.impl;

import com.psp.instapay.common.client.BankClient;
import com.psp.instapay.common.client.BankClientFactory;
import com.psp.instapay.common.exception.AccountNotFoundException;
import com.psp.instapay.common.exception.InsufficientBalanceException;
import com.psp.instapay.common.exception.TransactionException;
import com.psp.instapay.model.dto.request.BankRequest;
import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.TransactionResponse;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Transaction;
import com.psp.instapay.model.enums.BankTransactionStatus;
import com.psp.instapay.model.enums.TransactionStatus;
import com.psp.instapay.model.enums.TransactionType;
import com.psp.instapay.model.repository.AccountRepository;
import com.psp.instapay.model.repository.TransactionRepository;
import com.psp.instapay.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionTimedOutException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final BankClientFactory bankClientFactory;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional(timeout = 30)
    public TransactionResponse sendMoney(SendMoneyRequest request) {
        log.info("Initiating transaction for request: {}", request);
        Transaction transaction = null;

        try {
            // Step 1: Set up the transaction
            transaction = initiateTransaction(request);
            transactionRepository.save(transaction);
        } catch (RuntimeException e) {
            log.error("Transaction initiation failed: {}", e.getMessage());
            return TransactionResponse.builder()
                    .transactionId(transaction != null ? transaction.getId() : null)
                    .status(TransactionStatus.FAILED)
                    .message("Transaction initiation failed: " + e.getMessage())
                    .build();
        }

        // Step 2: Create bank requests for both source and destination accounts
        BankRequest sourceBankRequest = createBankRequest(
                request.getSourceAccountNumber(),
                TransactionType.WITHDRAWAL,
                request.getAmount());

        BankRequest destinationBankRequest = createBankRequest(
                request.getDestinationAccountNumber(),
                TransactionType.DEPOSIT,
                request.getAmount());
        
        // Step 3: Start the transaction process
        try {
            // Step 1: Get the bank clients for both source and destination banks
            BankClient sourceBankClient = getBankClient(transaction.getFromBank().getName());
            BankClient destinationBankClient = getBankClient(transaction.getToBank().getName());

            // Step 2: Request preparation from the source and destination banks and check their statuses
            TransactionStatus sourceBankStatus = requestPrepare(sourceBankClient, sourceBankRequest);
            TransactionStatus destinationBankStatus = requestPrepare(destinationBankClient, destinationBankRequest);

            if (!sourceBankStatus.equals(TransactionStatus.PREPARED) ||
                    !destinationBankStatus.equals(TransactionStatus.PREPARED)) {
                log.error("Preparation failed for transaction: {}", transaction.getId());

                handleRollback(sourceBankClient, destinationBankClient, transaction);

                transaction.setStatus(TransactionStatus.ROLLED_BACK);
                transactionRepository.save(transaction);

                throw new TransactionException("Transaction preparation failed");
            }

            transaction.setStatus(TransactionStatus.PREPARED);
            transactionRepository.save(transaction);

            // Step 4: Request commitment from both banks
            sourceBankStatus = requestCommit(sourceBankClient, transaction.getId());
            destinationBankStatus = requestCommit(destinationBankClient, transaction.getId());

            // Step 5: Check the status of both banks' statuses again
            if (!sourceBankStatus.equals(TransactionStatus.COMMITTED) ||
                    !destinationBankStatus.equals(TransactionStatus.COMMITTED)) {
                log.error("Commit failed for transaction: {}", transaction.getId());

                handleRollback(sourceBankClient, destinationBankClient, transaction);

                transaction.setStatus(TransactionStatus.ROLLED_BACK);
                transactionRepository.save(transaction);

                throw new TransactionException("Transaction commit failed");
            }
            
            transaction.setStatus(TransactionStatus.COMMITTED);
            transactionRepository.save(transaction);

            // Step 6: Update the account balances
            updateAccountBalance(transaction.getFromAccount().getAccountNumber(), sourceBankClient);
            updateAccountBalance(transaction.getToAccount().getAccountNumber(), destinationBankClient);

            // Step 7: Update the transaction in the database
            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            log.info("Transaction completed successfully: {}", transaction.getId());

            // Step 9: Return the transaction response
            return TransactionResponse.builder()
                    .transactionId(transaction.getId())
                    .status(transaction.getStatus())
                    .message("Transaction completed successfully")
                    .build();
        } catch (Exception e) {
            log.error("Transaction failed: {}", e.getMessage());
            return TransactionResponse.builder()
                    .transactionId(transaction.getId())
                    .status(transaction.getStatus())
                    .message(e.getMessage())
                    .build();
        }
    }

    private void updateAccountBalance(String accountNumber, BankClient bankClient) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Double newBalance = (Double) bankClient.getBalance(accountNumber).getData();
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void handleRollback(BankClient sourceBankClient, BankClient destinationBankClient, Transaction transaction) {
        TransactionStatus sourceBankStatus = requestRollback(sourceBankClient, transaction.getId());
        TransactionStatus destinationBankStatus = requestRollback(destinationBankClient, transaction.getId());

        if (!sourceBankStatus.equals(TransactionStatus.ROLLED_BACK) ||
                !destinationBankStatus.equals(TransactionStatus.ROLLED_BACK)) {
            log.error("Rollback failed for transaction: {}", transaction);
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new TransactionException("Transaction rollback failed");
        }
    }

    private TransactionStatus requestRollback(BankClient bankClient, Long transactionId) {
        TransactionResponse response = (TransactionResponse) bankClient.rollbackTransaction(transactionId).getData();
        return response.getStatus();
    }

    private TransactionStatus requestCommit(BankClient bankClient, Long transactionId) {
        TransactionResponse response = (TransactionResponse) bankClient.commitTransaction(transactionId).getData();
        return response.getStatus();
    }

    private TransactionStatus requestPrepare(BankClient bankClient, BankRequest request) {
        TransactionResponse response = (TransactionResponse) bankClient.prepareTransaction(request).getData();
        return response.getStatus();
    }

    private BankClient getBankClient(String bankName) {
        return bankClientFactory.getBankClient(bankName);
    }

    private BankRequest createBankRequest(String accountNumber, TransactionType type, Double amount) {
        return BankRequest.builder()
                .accountNumber(accountNumber)
                .type(type)
                .amount(amount)
                .build();
    }

    private Transaction initiateTransaction(SendMoneyRequest request) {
        Account sourceAccount = accountRepository.findByAccountNumberForUpdate(request.getSourceAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account destinationAccount = accountRepository.findByAccountNumberForUpdate(request.getDestinationAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        Double amount = request.getAmount();
        if (amount <= 0)
            throw new IllegalArgumentException("Transaction amount must be greater than zero");

        if (sourceAccount.getBalance() < request.getAmount())
            throw new InsufficientBalanceException("Insufficient funds in source account");

        return Transaction.builder()
                .fromAccount(sourceAccount)
                .toAccount(destinationAccount)
                .fromBank(sourceAccount.getBank())
                .toBank(destinationAccount.getBank())
                .amount(request.getAmount())
                .status(TransactionStatus.INITIATED)
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
