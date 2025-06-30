package com.psp.nbebank.common.exception;

/**
 * Custom exception to indicate an issue during a transaction process.
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * without being explicitly declared in the method signature.
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
}