package com.psp.cibbank.model.repository;

import com.psp.cibbank.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Transaction entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Finds a transaction by its ID with a lock for update.
     * This method is useful for scenarios where the transaction needs to be locked
     * to prevent concurrent modifications.
     *
     * @param id the ID of the transaction
     * @return an Optional containing the Transaction if found, or empty if not found
     */
    Optional<Transaction> findForUpdateById(Long id);
}