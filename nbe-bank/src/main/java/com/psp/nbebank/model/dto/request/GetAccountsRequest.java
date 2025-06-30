package com.psp.nbebank.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for requesting account details.
 * Contains information required to retrieve accounts associated with a card.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountsRequest {

    /**
     * The phone number associated with the account.
     */
    String phoneNumber;

    /**
     * The name of the bank. This field is required.
     */
    @NotNull(message = "Bank is required")
    String bankName;

    /**
     * The card number associated with the account. This field is required.
     */
    @NotNull(message = "Account number is required")
    String cardNumber;

    /**
     * The PIN associated with the card. This field is required.
     */
    @NotNull(message = "PIN is required")
    String pin;
}