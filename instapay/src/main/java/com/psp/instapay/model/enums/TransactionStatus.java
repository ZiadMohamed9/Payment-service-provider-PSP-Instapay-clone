package com.psp.instapay.model.enums;

/**
 * Enum representing the status of a transaction in the system.
 * Defines the various states a transaction can go through during its lifecycle.
 */
public enum TransactionStatus {
    /**
     * Initial state of the transaction.
     */
    INITIATED,

    /**
     * State indicating both banks are ready for the transaction.
     */
    PREPARED,

    /**
     * State indicating the transaction has been aborted.
     */
    ABORTED,

    /**
     * State indicating the transaction has been successfully completed.
     */
    COMMITTED,

    /**
     * State indicating the transaction has been rolled back after a failure.
     */
    ROLLED_BACK,

    /**
     * State indicating the transaction is in the process of being rolled back.
     */
    ROLLING_BACK,

    /**
     * State indicating the transaction has encountered an unrecoverable failure.
     */
    FAILED,

    /**
     * State indicating the transaction has timed out.
     */
    TIMEOUT,

    /**
     * State indicating the transaction has been completed successfully.
     */
    SUCCESS,
}