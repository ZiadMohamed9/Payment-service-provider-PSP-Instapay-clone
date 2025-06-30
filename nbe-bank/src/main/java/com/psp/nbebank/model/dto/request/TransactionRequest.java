package com.psp.nbebank.model.dto.request;

import com.psp.nbebank.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for transaction requests.
 * Contains the necessary information to initiate a transaction.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    /**
     * The account number associated with the transaction.
     * This field is required.
     */
    @NotNull(message = "Account number is required")
    private String accountNumber;

    /**
     * The type of the transaction (e.g., DEPOSIT, WITHDRAWAL).
     * This field is required.
     */
    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    /**
     * The amount involved in the transaction.
     * Must be greater than 0 and less than or equal to 50,000.
     * This field is required.
     */
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1", message = "Amount must be greater than 0")
    @DecimalMax(value = "50000", message = "Amount must be less than or equal to 50000")
    private Double amount;
}