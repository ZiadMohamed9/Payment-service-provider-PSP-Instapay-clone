package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.AccountNotFoundException;
import com.psp.nbebank.common.exception.TransactionException;
import com.psp.nbebank.model.dto.request.BankRequest;
import com.psp.nbebank.model.dto.response.TransactionResponse;
import com.psp.nbebank.model.entity.Account;
import com.psp.nbebank.model.entity.Transaction;
import com.psp.nbebank.model.enums.BankTransactionStatus;
import com.psp.nbebank.model.enums.TransactionStatus;
import com.psp.nbebank.model.enums.TransactionType;
import com.psp.nbebank.model.repository.AccountRepository;
import com.psp.nbebank.model.repository.TransactionRepository;
import com.psp.nbebank.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional(timeout = 10)
    public TransactionResponse prepareTransaction(BankRequest request) {
        try {
            // Step 1: Validate the request
            validateRequest(request);

            // Step 2: Initiate the transaction
            Transaction transaction = initiateTransaction(request);

            // Step 3: Save the transaction to the database
            transactionRepository.save(transaction);

            // Step 4: Return the transaction response
            return buildResponse(transaction.getId(), BankTransactionStatus.PREPARED, "Transaction prepared successfully");
        } catch (Exception e) {
            return buildResponse(null, BankTransactionStatus.ABORTED, "Transaction preparation failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional(timeout = 10)
    public TransactionResponse commitTransaction(Long transactionId) {
        Transaction transaction = getTransactionById(transactionId);

        try {
            updateAccountBalance(transaction);

            transaction.setStatus(TransactionStatus.COMMITTED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), BankTransactionStatus.COMMITTED, "Transaction committed successfully");
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.ABORTED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), BankTransactionStatus.ABORTED, "Transaction commit failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional(timeout = 10)
    public TransactionResponse rollbackTransaction(Long transactionId) {
        Transaction transaction = getTransactionById(transactionId);

        try {
            updateAccountBalance(transaction);

            transaction.setStatus(TransactionStatus.ROLLED_BACK);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), BankTransactionStatus.ROLLED_BACK, "Transaction rolled back successfully");
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), BankTransactionStatus.FAILED, "Transaction rollback failed: " + e.getMessage());
        }
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionException("Transaction not found"));
    }

    private void updateAccountBalance(Transaction transaction) {
        Account account = transaction.getAccount();

        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }

        switch (transaction.getStatus()) {
            case COMMITTED:
                if (transaction.getTransactionType() == TransactionType.WITHDRAWAL &&
                        checkBalance(account, transaction.getAmount())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }
                else if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                break;
            case ROLLED_BACK:
                if (transaction.getTransactionType() == TransactionType.WITHDRAWAL) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                else if (transaction.getTransactionType() == TransactionType.DEPOSIT &&
                        checkBalance(account, transaction.getAmount())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }
                break;
        }
        accountRepository.save(account);
    }

    private boolean checkBalance(Account account, Double amount) {
        return account.getBalance() >= amount;
    }

    private Transaction initiateTransaction(BankRequest request) {
        Account account = accountRepository.findByAccountNumberForUpdate(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return Transaction.builder()
                .Account(account)
                .amount(request.getAmount())
                .transactionType(request.getType())
                .status(TransactionStatus.PREPARED)
                .build();
    }

    private void validateRequest(BankRequest request) {
        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (request.getAccountNumber() == null || request.getAccountNumber().isEmpty()) {
            throw new IllegalArgumentException("Account number must not be null or empty");
        }
        if (request.getType() == null) {
            throw new IllegalArgumentException("Transaction type must not be null");
        }
        if (!accountRepository.existsByAccountNumber(request.getAccountNumber())) {
            throw new AccountNotFoundException("Account not found");
        }
    }

    private TransactionResponse buildResponse(Long transactionId, BankTransactionStatus status, String message) {
        return TransactionResponse.builder()
                .transactionId(transactionId)
                .status(status)
                .message(message)
                .build();
    }
}
