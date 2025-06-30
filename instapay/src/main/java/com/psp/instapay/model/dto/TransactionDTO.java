package com.psp.instapay.model.dto;

import com.psp.instapay.model.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for transaction details.
 * Represents the details of a transaction including accounts, banks, amount, status, and date.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    /**
     * The account number of the sender.
     * Represents the source account for the transaction.
     */
    private String fromAccountNumber;

    /**
     * The account number of the recipient.
     * Represents the destination account for the transaction.
     */
    private String toAccountNumber;

    /**
     * The name of the bank associated with the sender's account.
     */
    private String fromBankName;

    /**
     * The name of the bank associated with the recipient's account.
     */
    private String toBankName;

    /**
     * The amount of money being transferred in the transaction.
     */
    private Double amount;

    /**
     * The status of the transaction.
     * Indicates whether the transaction is pending, successful, or failed.
     */
    private TransactionStatus status;

    /**
     * The date and time when the transaction occurred.
     */
    private LocalDateTime transactionDate;
}