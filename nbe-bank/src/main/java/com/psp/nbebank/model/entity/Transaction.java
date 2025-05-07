package com.psp.nbebank.model.entity;

import com.psp.nbebank.model.enums.TransactionStatus;
import com.psp.nbebank.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account Account;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    public Transaction(Account account, Double amount, TransactionType transactionType, TransactionStatus status) {
        this.Account = account;
        this.amount = amount;
        this.transactionType = transactionType;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.transactionDate = LocalDateTime.now();
    }

}
