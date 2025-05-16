package com.psp.nbebank.model.repository;

import com.psp.nbebank.model.entity.Transaction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM Transaction t WHERE t.id = :id")
    Optional<Transaction> findForUpdateById(Long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT t FROM Transaction t WHERE t.id = :id")
    Optional<Transaction> findById(Long id);
}
