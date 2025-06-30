package com.psp.instapay.model.enums;

/**
 * Enum representing the types of transactions in the system.
 * Defines whether a transaction is a deposit or a withdrawal.
 */
public enum TransactionType {
    /**
     * Transaction type for adding funds to an account.
     */
    DEPOSIT,

    /**
     * Transaction type for removing funds from an account.
     */
    WITHDRAWAL,
}