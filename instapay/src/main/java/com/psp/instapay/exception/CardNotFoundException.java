package com.psp.instapay.exception;

/**
 * Exception thrown when a card is not found in the system.
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