package com.psp.instapay.common.exception;

/**
 * Exception thrown when an invalid role is encountered in the system.
 */
public class InvalidRoleException extends RuntimeException {

    /**
     * Constructs a new InvalidRoleException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public InvalidRoleException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidRoleException with the specified detail message
     * and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (a throwable that caused this exception)
     */
    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }
}