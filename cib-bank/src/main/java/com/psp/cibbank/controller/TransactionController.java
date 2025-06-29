package com.psp.cibbank.controller;

import com.psp.cibbank.model.dto.request.TransactionRequest;
import com.psp.cibbank.model.dto.response.TransactionResponse;
import com.psp.cibbank.model.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing transaction-related operations.
 * Provides endpoints to prepare, commit, and rollback transactions.
 */
@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Prepares a transaction based on the provided request data.
     *
     * @param request the transaction request containing details for preparation
     * @return a TransactionResponse containing the prepared transaction details
     */
    @PostMapping("/prepare")
    public TransactionResponse prepareTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.prepareTransaction(request);
    }

    /**
     * Commits a transaction based on the provided transaction ID.
     *
     * @param transactionId the ID of the transaction to commit
     * @return a TransactionResponse containing the committed transaction details
     */
    @PostMapping("/commit")
    public TransactionResponse commitTransaction(@RequestBody Long transactionId) {
        return transactionService.commitTransaction(transactionId);
    }

    /**
     * Rolls back a transaction based on the provided transaction ID.
     *
     * @param transactionId the ID of the transaction to roll back
     * @return a TransactionResponse containing the rolled-back transaction details
     */
    @PostMapping("/rollback")
    public TransactionResponse rollbackTransaction(@RequestBody Long transactionId) {
        return transactionService.rollbackTransaction(transactionId);
    }
}