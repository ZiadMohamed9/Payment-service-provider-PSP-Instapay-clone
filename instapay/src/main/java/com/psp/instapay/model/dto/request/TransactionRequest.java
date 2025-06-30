package com.psp.instapay.model.dto.request;

import com.psp.instapay.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transaction request.
 * Represents the request payload for performing a transaction.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    /**
     * The account number associated with the transaction.
     * This field is required and cannot be null.
     */
    private String accountNumber;

    /**
     * The type of the transaction (e.g., DEPOSIT, WITHDRAWAL).
     * This field is required and cannot be null.
     */
    private TransactionType type;

    /**
     * The amount involved in the transaction.
     * Must be greater than 0 and cannot be null.
     */
    private Double amount;
}