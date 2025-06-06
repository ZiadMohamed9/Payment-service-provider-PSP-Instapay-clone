package com.psp.instapay.model.enums;

public enum TransactionStatus {
    INITIATED,              // Initial state
    PREPARED,               // Both banks ready
    ABORTED,                // Transaction aborted
    COMMITTED,              // Successfully completed
    ROLLED_BACK,            // Rolled back after failure
    ROLLING_BACK,           // In the process of rolling back
    FAILED,                  // Unrecoverable failure
    TIMEOUT,                // Transaction timed out
    SUCCESS,               // Transaction completed successfully
}