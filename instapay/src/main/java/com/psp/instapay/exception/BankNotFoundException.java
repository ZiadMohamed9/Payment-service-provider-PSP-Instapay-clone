package com.psp.instapay.exception;

/**
 * Exception thrown when a bank is not found in the system.
 */
public class BankNotFoundException extends RuntimeException {

    /**
     * Constructs a new BankNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public BankNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new BankNotFoundException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public BankNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}