package com.psp.instapay.model.repository;

import com.psp.instapay.model.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Bank entities.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    /**
     * Finds a bank by its name.
     *
     * @param name The name of the bank to search for.
     * @return An Optional containing the bank if found, or empty otherwise.
     */
    Optional<Bank> findByName(String name);
}