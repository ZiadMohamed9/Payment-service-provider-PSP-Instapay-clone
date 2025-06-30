package com.psp.nbebank.common.exception;

/**
 * Custom exception to indicate that a card was not found.
 * This exception extends {@link RuntimeException}, allowing it to be thrown
 * without being explicitly declared in the method signature.
 */
public class CardNotFoundException extends RuntimeException {

    /**
     * Constructs a new CardNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CardNotFoundException(String message) {
        super(message);
    }
}