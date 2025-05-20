package com.psp.instapay.model.repository;

import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Transaction;
import com.psp.instapay.model.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Transaction> findAllByToAccount(Account toAccount);
}