package com.psp.instapay.common.exception;

/**
 * Exception thrown when an attempt is made to create an account
 * that already exists in the system.
 */
public class AccountAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new AccountAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public AccountAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new AccountAlreadyExistsException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public AccountAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}