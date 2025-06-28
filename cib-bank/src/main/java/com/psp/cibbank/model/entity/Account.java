package com.psp.cibbank.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Entity class representing a bank account in the CIB Bank system.
 * An account is associated with a card and has a unique account number.
 * It tracks the current balance, active status, and timestamps for creation and updates.
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Account {

    /**
     * Unique identifier for the account
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The card associated with this account
     */
    @ManyToOne
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    /**
     * Unique account number (16 digits)
     */
    @Column(name = "account_number", nullable = false, unique = true, length = 16)
    private String accountNumber;

    /**
     * Current balance of the account
     */
    @Column(name = "balance", nullable = false)
    private Double balance = 0.0;

    /**
     * Flag indicating if the account is active
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * Timestamp when the account was created
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the account was last updated
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a new account with specified details
     *
     * @param card The card associated with this account
     * @param accountNumber The unique account number
     * @param balance The initial balance
     * @param active The initial active status
     */
    public Account(Card card, String accountNumber, Double balance, boolean active) {
        this.card = card;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.active = active;
    }

    /**
     * Sets the creation timestamp before persisting the entity
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Updates the last modified timestamp before updating the entity
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
