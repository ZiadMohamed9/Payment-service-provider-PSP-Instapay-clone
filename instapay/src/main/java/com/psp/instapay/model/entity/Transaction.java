package com.psp.instapay.model.entity;

import com.psp.instapay.model.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a transaction in the system.
 * Maps to the "transaction" table in the database.
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    /**
     * The unique identifier for the transaction.
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The account from which the transaction is initiated.
     * Represents a many-to-one relationship with the Account entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    /**
     * The account to which the transaction is directed.
     * Represents a many-to-one relationship with the Account entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    /**
     * The bank associated with the sender's account.
     * Represents a many-to-one relationship with the Bank entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_bank_id", nullable = false)
    private Bank fromBank;

    /**
     * The bank associated with the recipient's account.
     * Represents a many-to-one relationship with the Bank entity.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_bank_id", nullable = false)
    private Bank toBank;

    /**
     * The amount of money being transferred in the transaction.
     * Cannot be null.
     */
    @Column(nullable = false)
    private Double amount;

    /**
     * The status of the transaction.
     * Indicates whether the transaction is pending, successful, or failed.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    /**
     * The date and time when the transaction occurred.
     * Automatically set to the current date and time when the transaction is created.
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    /**
     * Sets the transaction date to the current date and time before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }

    /**
     * Constructor to create a transaction with the specified details.
     *
     * @param fromAccount The account from which the transaction is initiated.
     * @param toAccount The account to which the transaction is directed.
     * @param fromBank The bank associated with the sender's account.
     * @param toBank The bank associated with the recipient's account.
     * @param amount The amount of money being transferred.
     * @param status The status of the transaction.
     */
    public Transaction(Account fromAccount, Account toAccount, Bank fromBank, Bank toBank, Double amount, TransactionStatus status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.fromBank = fromBank;
        this.toBank = toBank;
        this.amount = amount;
        this.status = status;
    }
}