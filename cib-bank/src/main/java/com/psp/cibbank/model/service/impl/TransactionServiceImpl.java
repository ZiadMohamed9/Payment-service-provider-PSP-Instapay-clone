package com.psp.cibbank.model.service.impl;

import com.psp.cibbank.common.exception.AccountNotFoundException;
import com.psp.cibbank.common.exception.TransactionException;
import com.psp.cibbank.common.util.EncryptionUtil;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final EncryptionUtil encryptionUtil;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public TransactionResponse prepareTransaction(BankRequest request) {
        log.info("Initiating transaction for account: {}", encryptionUtil.decrypt(request.getAccountNumber()));
        Transaction transaction = initiateTransaction(request);
        log.info("Transaction initiated successfully: {}", transaction.getId());

        try {
            transaction.setStatus(TransactionStatus.PREPARED);
            transactionRepository.save(transaction);
            log.info("Transaction prepared successfully: {}", transaction.getId());
        } catch (Exception e) {
            log.error("Error preparing transaction: {}", e.getMessage());
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            log.info("In preparation Transaction marked as failed: {}", transaction.getId());

            throw new RuntimeException(e);
        }

        return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction prepared successfully");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public TransactionResponse commitTransaction(Long transactionId) {
        log.info("Fetching transaction for commit: {}", transactionId);
        Transaction transaction = getTransactionById(transactionId);
        log.info("Transaction fetched successfully for commiting: {}", transaction);

        try {
            if (transaction.getStatus() != TransactionStatus.PREPARED) {
                log.info("Transaction is not in a valid state for commit: {}", transaction.getStatus());
                throw new TransactionException("Transaction is not in a valid state for commit");
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
            log.info("Transaction committed successfully: {}", transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction committed successfully");
        } catch (Exception e) {
            log.error("Error committing transaction: {}", e.getMessage());
            transaction.setStatus(TransactionStatus.ABORTED);
            transactionRepository.save(transaction);
            log.info("Transaction marked as aborted: {}", transaction);

            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public TransactionResponse rollbackTransaction(Long transactionId) {
        log.info("Fetching transaction for rollback: {}", transactionId);
        Transaction transaction = getTransactionById(transactionId);
        log.info("Transaction fetched successfully for rollback: {}", transaction);

        try {
            if (!transaction.getStatus().equals(TransactionStatus.COMMITTED)) {
                log.info("Transaction is not in a valid state for rollback: {}", transaction.getStatus());
                return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction rolled back successfully");
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
            log.info("Transaction rolled back successfully: {}", transaction);

            return buildResponse(transaction.getId(), transaction.getStatus(), "Transaction rolled back successfully");
        } catch (Exception e) {
            log.error("Error rolling back transaction: {}", e.getMessage());
            transaction.setStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            log.info("In rolling back Transaction marked as failed: {}", transaction);

            throw new RuntimeException(e);
        }
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findForUpdateById(transactionId)
                .orElseThrow(() -> new TransactionException("Transaction not found"));
    }

    private void updateAccountBalance(Transaction transaction) {
        String accountNumber = transaction.getAccount().getAccountNumber();
        Account account = accountRepository.findForUpdateByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }

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

    private boolean checkBalance(Account account, Double amount) {
        return account.getBalance() >= amount;
    }
    
    private Transaction initiateTransaction(BankRequest request) {
        validateRequest(request);

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
    }

    private TransactionResponse buildResponse(Long transactionId, TransactionStatus status, String message) {
        return TransactionResponse.builder()
                .transactionId(transactionId)
                .status(status)
                .message(message)
                .build();
    }
}
