package com.psp.instapay.exception;

/**
 * Exception thrown when a transaction-related error occurs in the system.
 */
public class TransactionException extends RuntimeException {

    /**
     * Constructs a new TransactionException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * Constructs a new TransactionException with the specified cause.
     *
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public TransactionException(Throwable cause) {
        super(cause);
    }
}