package com.psp.instapay.common.exception;

/**
 * Exception thrown when an account has insufficient balance to complete a transaction.
 */
public class InsufficientBalanceException extends RuntimeException {

    /**
     * Constructs a new InsufficientBalanceException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}