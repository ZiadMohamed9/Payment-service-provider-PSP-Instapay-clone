package com.psp.cibbank.common.exception;

/**
 * Custom exception to indicate that an account was not found.
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * without being explicitly declared in the method signature.
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
     * @param cause   the cause of the exception (a throwable that caused this exception)
     */
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new AccountNotFoundException with the specified cause.
     *
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}