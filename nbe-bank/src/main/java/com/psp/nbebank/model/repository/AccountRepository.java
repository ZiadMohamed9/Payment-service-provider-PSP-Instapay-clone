package com.psp.nbebank.model.repository;

import com.psp.nbebank.model.entity.Account;
import com.psp.nbebank.model.entity.Card;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Account entities in the CIB Bank system.
 * Provides methods for querying and manipulating account data with appropriate
 * locking mechanisms to ensure data consistency during concurrent operations.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by its account number with a pessimistic write lock.
     * This lock prevents other transactions from reading or writing to the account
     * until the current transaction completes.
     *
     * @param accountNumber The account number to search for
     * @return An Optional containing the account if found, or empty if not found
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByAccountNumber(String accountNumber);

    /**
     * Finds an account by its account number with a pessimistic read lock.
     * This lock allows other transactions to read but not write to the account
     * until the current transaction completes.
     *
     * @param accountNumber The account number to search for
     * @return An Optional containing the account if found, or empty if not found
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Account> findForShareByAccountNumber(String accountNumber);

    /**
     * Finds all accounts associated with a specific card with a pessimistic read lock.
     * This lock allows other transactions to read but not write to the accounts
     * until the current transaction completes.
     *
     * @param card The card whose associated accounts to find
     * @return A list of accounts associated with the card
     */
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Account> findForShareByCard(Card card);
}