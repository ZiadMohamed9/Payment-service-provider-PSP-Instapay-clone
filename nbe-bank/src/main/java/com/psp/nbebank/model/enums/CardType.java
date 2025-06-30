package com.psp.nbebank.model.enums;

/**
 * Enumeration of card types supported by the CIB Bank system.
 * This enum defines the different types of cards that can be issued to customers.
 */
public enum CardType {
    /**
     * A debit card that withdraws money directly from a linked checking account
     */
    DEBIT,

    /**
     * A credit card that allows borrowing funds with a promise to repay later
     */
    CREDIT,

    /**
     * A prepaid card that is loaded with funds in advance of purchases
     */
    PREPAID
}