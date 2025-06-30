package com.psp.nbebank.model.entity;

import com.psp.nbebank.model.enums.TransactionStatus;
import com.psp.nbebank.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a financial transaction in the CIB Bank system.
 * A transaction is associated with an account and records the amount, type, status, and date of the transaction.
 * This class is used to track all financial activities within the bank.
 */
@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Transaction {

    /**
     * Unique identifier for the transaction
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The account associated with this transaction
     */
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account Account;

    /**
     * The amount of money involved in the transaction
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * The type of transaction (e.g., DEPOSIT, WITHDRAWAL, TRANSFER)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;

    /**
     * The current status of the transaction (e.g., PENDING, COMPLETED, FAILED)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    /**
     * The date and time when the transaction occurred
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    /**
     * Constructor to create a new transaction with specified details
     *
     * @param account The account associated with this transaction
     * @param amount The amount of money involved
     * @param transactionType The type of transaction
     * @param status The initial status of the transaction
     */
    public Transaction(Account account, Double amount, TransactionType transactionType, TransactionStatus status) {
        this.Account = account;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }

    /**
     * Sets the transaction date to the current date and time before persisting the entity
     */
    @PrePersist
    protected void onCreate() {
        this.transactionDate = LocalDateTime.now();
    }

}