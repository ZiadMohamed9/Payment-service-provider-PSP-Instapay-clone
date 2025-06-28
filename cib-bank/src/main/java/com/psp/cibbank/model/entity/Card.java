package com.psp.cibbank.model.entity;

import com.psp.cibbank.model.enums.CardType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity class representing a bank card in the CIB Bank system.
 * A card is associated with a customer and contains sensitive information like card number, CVV, and PIN.
 * It also tracks the card type, expiration date, active status, and creation timestamp.
 */
@Entity
@Table(name = "card")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Card {

    /**
     * Unique identifier for the card
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The customer who owns this card
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
     * Unique card number (16 digits) - stored in encrypted form
     */
    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber; // Encrypted

    /**
     * Type of the card (e.g., CREDIT, DEBIT)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;

    /**
     * Date when the card expires
     */
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    /**
     * Card verification value (3 digits) - stored in encrypted form
     */
    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv; // Encrypted

    /**
     * Personal identification number (4 digits) - stored in encrypted form
     */
    @Column(name = "pin", nullable = false, length = 4)
    private String pin; // Encrypted

    /**
     * Flag indicating if the card is active
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Timestamp when the card was created
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Constructor to create a new card with specified details
     *
     * @param customer The customer who owns this card
     * @param cardNumber The unique card number
     * @param cardType The type of the card
     * @param expirationDate The expiration date of the card
     * @param cvv The card verification value
     * @param pin The personal identification number
     */
    public Card(Customer customer, String cardNumber, CardType cardType, LocalDate expirationDate, String cvv, String pin) {
        this.customer = customer;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.pin = pin;
    }

    /**
     * Sets the creation timestamp before persisting the entity
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
