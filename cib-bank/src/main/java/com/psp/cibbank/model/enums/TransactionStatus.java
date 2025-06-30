package com.psp.cibbank.model.enums;

/**
 * Enumeration of transaction statuses in the CIB Bank system.
 * This enum defines the different states a transaction can be in during its lifecycle,
 * following a two-phase commit protocol for ensuring transaction integrity across systems.
 */
public enum TransactionStatus {
    /**
     * Initial state when a transaction is first created
     */
    INITIATED,

    /**
     * Transaction is sending prepare requests to all participating systems
     */
    PREPARING,

    /**
     * All participating systems have acknowledged they are ready for the transaction
     */
    PREPARED,

    /**
     * Transaction is in the process of being aborted
     */
    ABORTING,

    /**
     * Transaction has been successfully aborted
     */
    ABORTED,

    /**
     * Transaction is sending commit requests to all participating systems
     */
    COMMITTING,

    /**
     * Transaction has been successfully committed by all participating systems
     */
    COMMITTED,

    /**
     * Transaction has been rolled back after a failure
     */
    ROLLED_BACK,

    /**
     * Transaction is in the process of rolling back
     */
    ROLLING_BACK,

    /**
     * Transaction has encountered an unrecoverable failure
     */
    FAILED,

    /**
     * Transaction has timed out waiting for a response
     */
    TIMEOUT,

    /**
     * Transaction has completed successfully
     */
    SUCCESS
}