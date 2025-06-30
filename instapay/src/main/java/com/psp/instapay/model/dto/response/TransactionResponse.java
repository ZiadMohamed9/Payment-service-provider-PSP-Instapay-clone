package com.psp.instapay.model.dto.response;

import com.psp.instapay.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transaction response.
 * Represents the response payload for a transaction operation.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    /**
     * The unique identifier of the transaction.
     * Used to track and reference the transaction.
     */
    private Long transactionId;

    /**
     * The status of the transaction.
     * Indicates whether the transaction was successful, failed, or pending.
     */
    private TransactionStatus status;

    /**
     * A message providing additional details about the transaction.
     * Typically used for success or error descriptions.
     */
    private String message;
}