package com.psp.cibbank.model.dto.response;

import com.psp.cibbank.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transaction responses.
 * Encapsulates the details of a transaction response returned by the API.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    /**
     * The unique identifier of the transaction.
     */
    private Long transactionId;

    /**
     * The status of the transaction (e.g., SUCCESS, FAILED, PENDING).
     */
    private TransactionStatus status;

    /**
     * A message providing additional information about the transaction.
     */
    private String message;
}