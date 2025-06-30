package com.psp.instapay.model.repository;

import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Transaction entities.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Retrieves all transactions where the specified account is either the sender or the receiver.
     * Applies a pessimistic read lock to prevent concurrent modifications.
     *
     * @param fromAccount The account from which the transaction originates.
     * @param toAccount The account to which the transaction is directed.
     * @return A list of transactions involving the specified accounts.
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Transaction> findAllByFromAccountOrToAccount(Account fromAccount, Account toAccount);
}