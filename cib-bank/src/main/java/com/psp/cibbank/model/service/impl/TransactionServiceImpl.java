package com.psp.cibbank.model.service.impl;

import com.psp.cibbank.common.exception.AccountNotFoundException;
import com.psp.cibbank.common.exception.TransactionException;
import com.psp.cibbank.model.dto.request.BankRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.entity.Account;
import com.psp.cibbank.model.entity.Transaction;
import com.psp.cibbank.model.enums.TransactionStatus;
import com.psp.cibbank.model.enums.TransactionType;
import com.psp.cibbank.model.repository.AccountRepository;
import com.psp.cibbank.model.repository.TransactionRepository;
import com.psp.cibbank.model.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional(timeout = 10)
    public TransactionResponse prepareTransaction(BankRequest request) {
        try {
            // Step 1: Validate the request
            validateRequest(request);
        } catch (RuntimeException e) {
            return buildResponse(null, TransactionStatus.FAILED, "Transaction preparation failed: " + e.getMessage());
        }

        // Step 2: Initiate the transaction
        Transaction transaction = initiateTransaction(request);

        try {
            // Step 3: Save the transaction to the database
            transactionRepository.save(transaction);

            // Step 4: Return the transaction response
            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction prepared successfully");
        } catch (RuntimeException e) {
            // Step 5: Handle any exceptions that occur during the transaction
            transaction.setStatus(TransactionStatus.ABORTED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction preparation failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional(timeout = 10)
    public TransactionResponse commitTransaction(Long transactionId) {
        Transaction transaction = getTransactionById(transactionId);

        try {
            transaction.setStatus(TransactionStatus.COMMITTING);
            transactionRepository.save(transaction);

            updateAccountBalance(transaction);

            transaction.setStatus(TransactionStatus.COMMITTED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction committed successfully");
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.ABORTED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction commit failed: " + e.getMessage());
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

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction rolled back successfully");
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction rollback failed: " + e.getMessage());
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
            case COMMITTING:
                if (transaction.getTransactionType() == TransactionType.WITHDRAWAL &&
                checkBalance(account, transaction.getAmount())) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                }
                else if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                break;
            case COMMITTED:
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
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
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

    private TransactionResponse buildResponse(Long transactionId, TransactionStatus status, String message) {
        return TransactionResponse.builder()
                .transactionId(transactionId)
                .status(status)
                .message(message)
                .build();
    }
}
