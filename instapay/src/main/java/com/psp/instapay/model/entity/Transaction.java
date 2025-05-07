package com.psp.instapay.model.entity;

import com.psp.instapay.model.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    private Account toAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_bank_id", nullable = false)
    private Bank fromBank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_bank_id", nullable = false)
    private Bank toBank;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @PrePersist
    protected void onCreate() {
        transactionDate = LocalDateTime.now();
    }

    public Transaction(Account fromAccount, Account toAccount, Bank fromBank, Bank toBank, Double amount, TransactionStatus status) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.fromBank = fromBank;
        this.toBank = toBank;
        this.amount = amount;
        this.status = status;
    }
} 