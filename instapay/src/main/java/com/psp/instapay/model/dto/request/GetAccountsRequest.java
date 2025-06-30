package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for retrieving accounts request.
 * Represents the request payload for fetching account details based on user input.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountsRequest {

    /**
     * The phone number associated with the account.
     * Can be null or empty if not required for the request.
     */
    String phoneNumber;

    /**
     * The name of the bank associated with the account.
     * Cannot be null and must be provided in the request.
     */
    @NotNull(message = "Bank is required")
    String bankName;

    /**
     * The card number associated with the account.
     * Must be exactly 16 digits long and cannot be null.
     */
    @NotNull(message = "Account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    String cardNumber;

    /**
     * The PIN associated with the account.
     * Must be exactly 4 digits long and cannot be null.
     */
    @NotNull(message = "PIN is required")
    @Size(min = 4, max = 4, message = "PIN must be 4 digits")
    String pin;
}