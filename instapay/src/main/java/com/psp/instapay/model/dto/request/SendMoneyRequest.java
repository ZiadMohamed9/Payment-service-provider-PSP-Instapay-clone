package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for send money request.
 * Represents the request payload for transferring money between accounts.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyRequest {

    /**
     * The source account number from which the money will be sent.
     * Must be exactly 16 digits long and cannot be null.
     */
    @NotNull(message = "Source account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String sourceAccountNumber;

    /**
     * The destination account number to which the money will be sent.
     * Must be exactly 16 digits long and cannot be null.
     */
    @NotNull(message = "Destination account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    private String destinationAccountNumber;

    /**
     * The amount of money to be transferred.
     * Must be greater than 0 and less than or equal to 50,000.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Transaction type is required")
    @DecimalMin(value = "1", message = "Amount must be greater than 0")
    @DecimalMax(value = "50000", message = "Amount must be less than or equal to 50000")
    private Double amount;
}