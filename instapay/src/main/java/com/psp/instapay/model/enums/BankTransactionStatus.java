package com.psp.instapay.model.enums;

public enum BankTransactionStatus {
    READY,      // Bank ready to process
    PREPARED,   // Resources locked
    COMMITTED,  // Transaction completed
    ABORTED,    // Transaction aborted
    ROLLED_BACK // Transaction rolled back
}