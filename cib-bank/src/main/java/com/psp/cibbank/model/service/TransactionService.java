package com.psp.cibbank.model.service;

import com.psp.cibbank.model.dto.request.TransactionRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.entity.Transaction;

/**
 * Service interface for transaction-related operations.
 * Provides methods to prepare, commit, and rollback transactions.
 */
public interface TransactionService {

    /**
     * Prepares a transaction based on the provided request.
     *
     * @param request the request object containing transaction details
     * @return a TransactionResponse object containing the prepared transaction details
     */
    TransactionResponse prepareTransaction(TransactionRequest request);

    /**
     * Commits a transaction based on the provided transaction ID.
     *
     * @param transactionId the unique identifier of the transaction to be committed
     * @return a TransactionResponse object containing the committed transaction details
     */
    TransactionResponse commitTransaction(Long transactionId);

    /**
     * Rolls back a transaction based on the provided transaction ID.
     *
     * @param transactionId the unique identifier of the transaction to be rolled back
     * @return a TransactionResponse object containing the rolled-back transaction details
     */
    TransactionResponse rollbackTransaction(Long transactionId);
}