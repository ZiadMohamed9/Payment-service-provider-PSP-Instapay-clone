package com.psp.nbebank.common.exception;

/**
 * Custom exception to indicate that a customer was not found.
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * without being explicitly declared in the method signature.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructs a new CustomerNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new CustomerNotFoundException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause   the cause of the exception (a throwable that caused this exception)
     */
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}