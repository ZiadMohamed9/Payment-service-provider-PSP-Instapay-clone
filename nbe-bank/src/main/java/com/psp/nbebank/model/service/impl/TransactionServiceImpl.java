package com.psp.nbebank.model.service.impl;

import com.psp.nbebank.common.exception.AccountNotFoundException;
import com.psp.nbebank.common.exception.TransactionException;
import com.psp.nbebank.common.util.EncryptionUtil;
import com.psp.nbebank.model.dto.request.TransactionRequest;
import com.psp.nbebank.model.dto.response.TransactionResponse;
import com.psp.nbebank.model.entity.Account;
import com.psp.nbebank.model.entity.Transaction;
import com.psp.nbebank.model.enums.TransactionStatus;
import com.psp.nbebank.model.enums.TransactionType;
import com.psp.nbebank.model.repository.AccountRepository;
import com.psp.nbebank.model.repository.TransactionRepository;
import com.psp.nbebank.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the TransactionService interface.
 * Provides methods to prepare, commit, and rollback transactions.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EncryptionUtil encryptionUtil;

    /**
     * Prepares a transaction based on the provided request.
     * Logs the process and updates the transaction status to PREPARED.
     *
     * @param request the request object containing transaction details
     * @return a TransactionResponse object containing the prepared transaction details
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransactionResponse prepareTransaction(TransactionRequest request) {
        Transaction transaction;
        try {
            log.info("Initiating transaction for account: {}", encryptionUtil.decrypt(request.getAccountNumber()));

            transaction = initiateTransaction(request);

            log.info("Transaction initiated successfully: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Error initiating transaction: {}", e.getMessage());

            throw e;
        }

        String message;
        try {
            log.info("Preparing transaction: {}", transaction);

            transaction.setStatus(TransactionStatus.PREPARED);
            transactionRepository.save(transaction);

            message = "Transaction prepared successfully: " + transaction.getId();

            log.info("Transaction prepared successfully: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Error preparing transaction: {}", e.getMessage());

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            message = "Transaction preparation failed: " + transaction.getId() + " - " + e.getMessage();

            log.info("In preparation Transaction marked as failed: {}", transaction.getId());
        }

        return buildResponse(transaction.getId(), transaction.getStatus(), message);
    }

    /**
     * Commits a transaction based on the provided transaction ID.
     * Updates the account balance and marks the transaction as COMMITTED.
     *
     * @param transactionId the unique identifier of the transaction to be committed
     * @return a TransactionResponse object containing the committed transaction details
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransactionResponse commitTransaction(Long transactionId) {
        log.info("Fetching transaction for commit: {}", transactionId);

        Transaction transaction = getTransactionById(transactionId);

        log.info("Transaction fetched successfully for commiting: {}", transaction);

        String message;
        try {
            if (!transaction.getStatus().equals(TransactionStatus.PREPARED)) {
                log.info("Transaction is not prepared for commit: {}", transaction.getStatus());
                throw new TransactionException("Transaction is not prepared for commit");
            }

            log.info("Committing transaction: {}", transaction);

            transaction.setStatus(TransactionStatus.COMMITTING);
            transactionRepository.save(transaction);

            log.info("Updating account balance for transaction: {}", transaction);

            updateAccountBalance(transaction);

            log.info("Account balance updated successfully for transaction: {}", transaction);

            log.info("Marking transaction as committed: {}", transaction);

            transaction.setStatus(TransactionStatus.COMMITTED);
            transactionRepository.save(transaction);

            message = "Transaction committed successfully: " + transaction.getId();

            log.info("Transaction committed successfully: {}", transaction);
        } catch (Exception e) {
            log.error("Error committing transaction: {}", e.getMessage());

            transaction.setStatus(TransactionStatus.ABORTED);
            transactionRepository.save(transaction);

            message = "Transaction commit failed: " + transaction.getId() + " - " + e.getMessage();

            log.info("Transaction marked as aborted: {}", transaction);
        }

        return buildResponse(transaction.getId(), transaction.getStatus(), message);
    }

    /**
     * Rolls back a transaction based on the provided transaction ID.
     * Updates the account balance and marks the transaction as ROLLED_BACK.
     *
     * @param transactionId the unique identifier of the transaction to be rolled back
     * @return a TransactionResponse object containing the rolled-back transaction details
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public TransactionResponse rollbackTransaction(Long transactionId) {
        log.info("Fetching transaction for rollback: {}", transactionId);

        Transaction transaction = getTransactionById(transactionId);

        log.info("Transaction fetched successfully for rollback: {}", transaction);

        String message;
        try {
            TransactionStatus status = transaction.getStatus();
            if (status.equals(TransactionStatus.PREPARED) || status.equals(TransactionStatus.INITIATED)) {
                log.info("Marking Transaction as ROLLED_BACK: {}", transaction.getStatus());

                transaction.setStatus(TransactionStatus.ROLLED_BACK);
                transactionRepository.save(transaction);

                return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction rolled back successfully");
            }

            if (!transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
                log.info("Transaction is not committed to rollback: {}", transaction.getStatus());
                throw new TransactionException("Transaction is not committed to rollback");
            }

            log.info("Rolling back transaction: {}", transaction);

            transaction.setStatus(TransactionStatus.ROLLING_BACK);
            transactionRepository.save(transaction);

            log.info("Updating account balance for rollback transaction: {}", transaction);

            updateAccountBalance(transaction);

            log.info("Account balance updated successfully for rollback transaction: {}", transaction);

            log.info("Marking transaction as rolled back: {}", transaction);

            transaction.setStatus(TransactionStatus.ROLLED_BACK);
            transactionRepository.save(transaction);

            message = "Transaction rolled back successfully: " + transaction.getId();

            log.info("Transaction rolled back successfully: {}", transaction);
        } catch (Exception e) {
            log.error("Error rolling back transaction: {}", e.getMessage());

            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            message = "Transaction rollback failed: " + transaction.getId() + " - " + e.getMessage();

            log.info("In rolling back Transaction marked as failed: {}", transaction);
        }

        return buildResponse(transaction.getId(), transaction.getStatus(), message);
    }

    /**
     * Retrieves a transaction by its ID.
     *
     * @param transactionId the unique identifier of the transaction
     * @return the Transaction object
     * @throws TransactionException if the transaction is not found
     */
    private Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findForUpdateById(transactionId)
                .orElseThrow(() -> new TransactionException("Transaction not found"));
    }

    /**
     * Updates the account balance based on the transaction details.
     *
     * @param transaction the transaction object containing account and amount details
     * @throws AccountNotFoundException if the account is not found
     */
    private void updateAccountBalance(Transaction transaction) {
        String accountNumber = transaction.getAccount().getAccountNumber();
        Account account = accountRepository.findForUpdateByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        switch (transaction.getStatus()) {
            case COMMITTING:
                if (transaction.getTransactionType().equals(TransactionType.WITHDRAWAL) &&
                checkBalance(account, transaction.getAmount())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }
                else if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                break;
            case ROLLING_BACK:
                if (transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                else if (transaction.getTransactionType().equals(TransactionType.DEPOSIT) &&
                checkBalance(account, transaction.getAmount())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }
                break;
        }
        accountRepository.save(account);
    }

    /**
     * Checks if the account has sufficient balance for the transaction.
     *
     * @param account the account object
     * @param amount the transaction amount
     * @return true if the account has sufficient balance, false otherwise
     */
    private boolean checkBalance(Account account, Double amount) {
        return account.getBalance() >= amount;
    }

    /**
     * Initiates a transaction based on the provided request.
     *
     * @param request the request object containing transaction details
     * @return the initiated Transaction object
     * @throws AccountNotFoundException if the account is not found
     */
    private Transaction initiateTransaction(TransactionRequest request) {
        String decryptedAccountNumber = encryptionUtil.decrypt(request.getAccountNumber());

        Account account = accountRepository.findForUpdateByAccountNumber(decryptedAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        Transaction transaction = Transaction.builder()
                .Account(account)
                .amount(request.getAmount())
                .transactionType(request.getType())
                .status(TransactionStatus.INITIATED)
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    /**
     * Builds a TransactionResponse object based on the provided details.
     *
     * @param transactionId the unique identifier of the transaction
     * @param status the status of the transaction
     * @param message the message describing the transaction status
     * @return a TransactionResponse object
     */
    private TransactionResponse buildResponse(Long transactionId, TransactionStatus status, String message) {
        return TransactionResponse.builder()
                .transactionId(transactionId)
                .status(status)
                .message(message)
                .build();
    }
}