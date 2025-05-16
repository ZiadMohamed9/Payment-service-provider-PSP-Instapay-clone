package com.psp.cibbank.model.enums;

public enum TransactionStatus {
    INITIATED,              // Initial state
    PREPARING,              // Sent prepare requests
    PREPARED,               // Both banks ready
    ABORTING,               // Sending abort requests
    ABORTED,                // Transaction aborted
    COMMITTING,             // Sending commit requests
    COMMITTED,              // Successfully completed
    ROLLED_BACK,            // Rolled back after failure
    ROLLING_BACK,           // In the process of rolling back
    FAILED,                  // Unrecoverable failure
    TIMEOUT,                // Transaction timed out
    SUCCESS,               // Transaction completed successfully
}
