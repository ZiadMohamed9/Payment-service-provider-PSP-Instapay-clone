package com.psp.instapay.common.exception;

/**
 * Exception thrown when an account is not found in the system.
 */
public class AccountNotFoundException extends RuntimeException {

    /**
     * Constructs a new AccountNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public AccountNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountNotFoundException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}