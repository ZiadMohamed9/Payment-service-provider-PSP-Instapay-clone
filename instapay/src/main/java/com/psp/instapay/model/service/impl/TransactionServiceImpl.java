package com.psp.instapay.model.service.impl;

import com.psp.instapay.client.BankClient;
import com.psp.instapay.client.BankClientFactory;
import com.psp.instapay.common.exception.AccountNotFoundException;
import com.psp.instapay.common.exception.InsufficientBalanceException;
import com.psp.instapay.common.exception.TransactionException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.common.mapper.TransactionMapper;
import com.psp.instapay.common.util.EncryptionUtil;
import com.psp.instapay.model.dto.TransactionDTO;
import com.psp.instapay.model.dto.request.TransactionRequest;
import com.psp.instapay.model.dto.request.SendMoneyRequest;
import com.psp.instapay.model.dto.response.TransactionResponse;
import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Transaction;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.enums.TransactionStatus;
import com.psp.instapay.model.enums.TransactionType;
import com.psp.instapay.model.repository.AccountRepository;
import com.psp.instapay.model.repository.TransactionRepository;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the TransactionService interface.
 * Provides methods for managing transactions, including sending money and retrieving transaction history.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BankClientFactory bankClientFactory;
    private final EncryptionUtil encryptionUtil;
    private final TransactionMapper transactionMapper;

    /**
     * Sends money from one account to another.
     * Handles transaction preparation, commitment, and rollback in case of failure.
     *
     * @param request The request containing details of the transaction, such as source and destination accounts and the amount.
     * @return A TransactionResponse containing the status and details of the completed transaction.
     * @throws RuntimeException If any error occurs during the transaction process.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, noRollbackFor = Exception.class)
    public TransactionResponse sendMoney(SendMoneyRequest request) {
        // Step 1: Validate the request and initiate the transaction
        log.info("Initiating transaction for request: {}", request);

        Transaction transaction;
        try {
            transaction = initiateTransaction(request);
            transactionRepository.save(transaction);
        } catch (RuntimeException e) {
            log.error("Transaction initiation failed: {}", e.getMessage());

            throw e;
        }

        log.info("Transaction initiated successfully: {}", transaction.getId());


        // Step 2: Create bank requests for both source and destination accounts
        log.info("Creating bank requests for transaction: {}", transaction.getId());


        // Encrypt the account numbers
        String encryptedSourceAccountNumber = encryptionUtil.encrypt(request.getSourceAccountNumber());
        String encryptedDestinationAccountNumber = encryptionUtil.encrypt(request.getDestinationAccountNumber());

        TransactionRequest sourceBankRequest = createTransactionRequest(
                encryptedSourceAccountNumber,
                TransactionType.WITHDRAWAL,
                request.getAmount());

        TransactionRequest destinationBankRequest = createTransactionRequest(
                encryptedDestinationAccountNumber,
                TransactionType.DEPOSIT,
                request.getAmount());

        log.info("Bank requests created successfully for transaction: {}", transaction.getId());


        // Step 3: Start the transaction process
        // Step 1: Get the bank clients for both source and destination banks
        log.info("Getting bank clients for transaction: {}", transaction.getId());

        BankClient sourceBankClient;
        BankClient destinationBankClient;
        try {
            sourceBankClient = getBankClient(transaction.getFromBank().getName());
            destinationBankClient = getBankClient(transaction.getToBank().getName());
        } catch (Exception e) {
            log.error("Failed to get bank clients: {}", e.getMessage());

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            throw new RuntimeException("Failed to get bank clients");
        }

        log.info("Bank clients obtained successfully for transaction: {}", transaction.getId());


        Long sourceTransactionId = null;
        Long destinationTransactionId = null;
        try {
            // Step 2: Request preparation from the source and destination banks and check their statuses
            log.info("Requesting preparation from banks for transaction: {}, {}", transaction.getId(), transaction.getStatus());

            TransactionResponse sourceBankResponse = requestPrepare(sourceBankClient, sourceBankRequest);
            TransactionStatus sourceBankStatus = sourceBankResponse.getStatus();
            sourceTransactionId = sourceBankResponse.getTransactionId();

            TransactionResponse destinationBankResponse = requestPrepare(destinationBankClient, destinationBankRequest);
            TransactionStatus destinationBankStatus = destinationBankResponse.getStatus();
            destinationTransactionId = destinationBankResponse.getTransactionId();

            log.info("Banks responded successfully for transaction preparation: {}, {}", transaction.getId(), transaction.getStatus());


            log.info("Preparation statuses: source: {}, destination: {}", sourceBankStatus, destinationBankStatus);

            if (!sourceBankStatus.equals(TransactionStatus.PREPARED) ||
                    !destinationBankStatus.equals(TransactionStatus.PREPARED)) {
                log.error("Preparation failed for transaction: {}, {}", transaction.getId(), transaction.getStatus());

                throw new TransactionException("Transaction preparation failed");
            }


            log.info("Transaction preparation completed successfully: {}, {}", transaction.getId(), transaction.getStatus());

            transaction.setStatus(TransactionStatus.PREPARED);
            transactionRepository.save(transaction);

            log.info("Transaction marked as prepared: {}, {}", transaction.getId(), transaction.getStatus());


            // Step 4: Request commitment from both banks
            log.info("Requesting commit from banks for transaction: {}, {}", transaction.getId(), transaction.getStatus());

            sourceBankStatus = requestCommit(sourceBankClient, sourceTransactionId);
            destinationBankStatus = requestCommit(destinationBankClient, destinationTransactionId);

            log.info("Banks responded successfully for transaction commit: {}, {}", transaction.getId(), transaction.getStatus());


            // Step 5: Check the status of both banks' statuses again
            log.info("Commit statuses: source: {}, destination: {}", sourceBankStatus, destinationBankStatus);
            if (!sourceBankStatus.equals(TransactionStatus.COMMITTED) ||
                    !destinationBankStatus.equals(TransactionStatus.COMMITTED)) {
                log.error("Commit failed for transaction: {}, {}", transaction.getId(), transaction.getStatus());

                throw new TransactionException("Transaction commit failed");
            }


            log.info("Transaction committed successfully: {}, {}", transaction.getId(), transaction.getStatus());

            transaction.setStatus(TransactionStatus.COMMITTED);
            transactionRepository.save(transaction);

            log.info("Transaction marked as committed: {}, {}", transaction.getId(), transaction.getStatus());


            // Step 6: Update the account balances
            log.info("Updating account balances for transaction: {}, {}", transaction.getId(), transaction.getStatus());

            updateAccountBalance(transaction.getFromAccount().getAccountNumber(), sourceBankClient);
            updateAccountBalance(transaction.getToAccount().getAccountNumber(), destinationBankClient);

            log.info("Account balances updated successfully for transaction: {}, {}", transaction.getId(), transaction.getStatus());


            // Step 7: Update the transaction in the database
            log.info("Marking transaction as successful: {}, {}", transaction.getId(), transaction.getStatus());

            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transaction);

            log.info("Transaction marked as successful: {}, {}", transaction.getId(), transaction.getStatus());


            // Step 9: Return the transaction response
            return TransactionResponse.builder()
                    .transactionId(transaction.getId())
                    .status(transaction.getStatus())
                    .message("Transaction completed successfully")
                    .build();
        } catch (Exception e) {
            log.error("Transaction failed: {}", e.getMessage());

            if (sourceTransactionId != null)
                handleRollback(sourceBankClient, sourceTransactionId);

            if (destinationTransactionId != null)
                handleRollback(destinationBankClient, destinationTransactionId);

            transaction.setStatus(TransactionStatus.ROLLED_BACK);
            transactionRepository.save(transaction);

            log.info("Transaction rolled back successfully: {}, {}", transaction.getId(), transaction.getStatus());


            throw e;
        }
    }

    /**
     * Retrieves the transaction history for the authenticated user.
     *
     * @return A list of transactions as TransactionDTOs.
     * @throws UserNotFoundException If the authenticated user is not found.
     */
    @Override
    @Transactional
    public List<TransactionDTO> getTransactionHistory() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Transaction> transactions = new ArrayList<>();
        for (Account account : user.getAccounts()) {
            List<Transaction> accountTransactions = transactionRepository.findAllByFromAccountOrToAccount(account, account);
            transactions.addAll(accountTransactions);
        }

        if (transactions.isEmpty()) {
            log.info("No transactions found for user: {}", username);
            return List.of();
        }

        log.info("Transaction history retrieved successfully for user: {}", username);

        return transactions.stream()
                .map(transactionMapper::toTransactionDTO)
                .toList();
    }

    /**
     * Updates the account balance for a given account number.
     *
     * @param accountNumber The account number to update.
     * @param bankClient The bank client to use for fetching the new balance.
     * @throws AccountNotFoundException If the account is not found.
     */
    private void updateAccountBalance(String accountNumber, BankClient bankClient) {
        Account account = accountRepository.findForUpdateByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        String encryptedAccountNumber = encryptionUtil.encrypt(accountNumber);
        Double newBalance = bankClient.getBalance(encryptedAccountNumber);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    /**
     * Handles the rollback of a transaction by requesting the bank to roll back the transaction.
     *
     * @param bankClient The bank client to use for the rollback request.
     * @param bankTransactionId The ID of the transaction to roll back.
     * @throws TransactionException If the rollback request fails or the bank does not respond with a successful status.
     */
    private void handleRollback(BankClient bankClient, Long bankTransactionId) {
        log.info("Rolling back transaction: {}", bankTransactionId);

        TransactionStatus bankStatus;
        try {
            log.info("Requesting rollback from bank for transaction: {}", bankTransactionId);

            bankStatus = requestRollback(bankClient, bankTransactionId);

            log.info("Bank responded successfully for transaction rollback: {}", bankStatus);


            if (!bankStatus.equals(TransactionStatus.ROLLED_BACK)) {
                throw new TransactionException("Transaction rollback failed");
            }
        } catch (Exception e) {
            log.error("Rollback request failed: {}", e.getMessage());

            throw e;
        }
    }

    /**
     * Requests a rollback from the bank for a given transaction ID.
     *
     * @param bankClient The bank client to use for the rollback request.
     * @param transactionId The ID of the transaction to roll back.
     * @return The status of the rollback request.
     */
    private TransactionStatus requestRollback(BankClient bankClient, Long transactionId) {
        return bankClient.rollbackTransaction(transactionId).getStatus();
    }

    /**
     * Requests a commit from the bank for a given transaction ID.
     *
     * @param bankClient The bank client to use for the commit request.
     * @param transactionId The ID of the transaction to commit.
     * @return The status of the commit request.
     */
    private TransactionStatus requestCommit(BankClient bankClient, Long transactionId) {
        return bankClient.commitTransaction(transactionId).getStatus();
    }

    /**
     * Requests preparation from the bank for a given transaction request.
     *
     * @param bankClient The bank client to use for the preparation request.
     * @param request The transaction request containing account number, type, and amount.
     * @return The response from the bank containing the transaction ID and status.
     */
    private TransactionResponse requestPrepare(BankClient bankClient, TransactionRequest request) {
        return bankClient.prepareTransaction(request);
    }

    /**
     * Retrieves the bank client for a given bank name.
     *
     * @param bankName The name of the bank.
     * @return The bank client for the specified bank.
     */
    private BankClient getBankClient(String bankName) {
        return bankClientFactory.getBankClient(bankName);
    }

    /**
     * Creates a transaction request for the bank.
     *
     * @param accountNumber The account number associated with the transaction.
     * @param type The type of transaction (e.g., withdrawal, deposit).
     * @param amount The amount involved in the transaction.
     * @return A TransactionRequest object containing the details of the transaction.
     */
    private TransactionRequest createTransactionRequest(String accountNumber, TransactionType type, Double amount) {
        return TransactionRequest.builder()
                .accountNumber(accountNumber)
                .type(type)
                .amount(amount)
                .build();
    }

    /**
     * Initiates a transaction by validating the request and retrieving the source and destination accounts.
     *
     * @param request The request containing details of the transaction, such as source and destination accounts and the amount.
     * @return A Transaction object representing the initiated transaction.
     * @throws UserNotFoundException If the authenticated user is not found.
     * @throws AccountNotFoundException If either the source or destination account is not found.
     * @throws InsufficientBalanceException If the source account does not have enough balance for the transaction.
     * @throws TransactionException If the source and destination accounts are the same.
     */
    private Transaction initiateTransaction(SendMoneyRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Account sourceAccount = accountRepository.findForUpdateByUserAndAccountNumber(user, request.getSourceAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account destinationAccount = accountRepository.findForUpdateByAccountNumber(request.getDestinationAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        if (sourceAccount.getId().equals(destinationAccount.getId()))
            throw new TransactionException("Source and destination accounts cannot be the same");

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
