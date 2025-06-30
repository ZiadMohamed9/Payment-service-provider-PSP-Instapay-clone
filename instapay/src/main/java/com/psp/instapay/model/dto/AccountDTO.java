package com.psp.instapay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for account details.
 * Represents the account information including account number, bank name, and balance.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    /**
     * The unique account number.
     * Used to identify the account.
     */
    private String accountNumber;

    /**
     * The name of the bank associated with the account.
     */
    private String bankName;

    /**
     * The current balance of the account.
     * Represents the available funds in the account.
     */
    private Double balance;
}