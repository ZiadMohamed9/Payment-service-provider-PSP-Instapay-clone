package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for account details request.
 * Represents the request payload for retrieving account details.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsRequest {

    /**
     * The account number associated with the account.
     * Must be exactly 16 digits long and cannot be null.
     */
    @NotNull(message = "Account number is required")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    String accountNumber;
}