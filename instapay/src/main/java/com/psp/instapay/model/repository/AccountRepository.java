package com.psp.instapay.model.repository;

import com.psp.instapay.model.entity.Account;
import com.psp.instapay.model.entity.Bank;
import com.psp.instapay.model.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Account entities.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Finds an account by the associated user and account number.
     *
     * @param user The user associated with the account.
     * @param accountNumber The account number to search for.
     * @return An Optional containing the account if found, or empty otherwise.
     */
    Optional<Account> findByUserAndAccountNumber(User user, String accountNumber);

    /**
     * Retrieves all accounts associated with a specific user.
     *
     * @param user The user whose accounts are to be retrieved.
     * @return A list of accounts associated with the user.
     */
    List<Account> findAllByUser(User user);

    /**
     * Retrieves all accounts associated with a specific user and bank.
     *
     * @param user The user whose accounts are to be retrieved.
     * @param bank The bank associated with the accounts.
     * @return A list of accounts associated with the user and bank.
     */
    List<Account> findAllByUserAndBank(User user, Bank bank);

    /**
     * Checks if an account exists by its account number.
     *
     * @param accountNumber The account number to check.
     * @return True if the account exists, false otherwise.
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Checks if an account exists for a specific user and account number.
     *
     * @param user The user associated with the account.
     * @param accountNumber The account number to check.
     * @return True if the account exists, false otherwise.
     */
    boolean existsByUserAndAccountNumber(User user, String accountNumber);

    /**
     * Finds an account for update by its account number with a pessimistic write lock.
     *
     * @param accountNumber The account number to search for.
     * @return An Optional containing the account if found, or empty otherwise.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByAccountNumber(String accountNumber);

    /**
     * Finds an account for update by the associated user and account number with a pessimistic write lock.
     *
     * @param user The user associated with the account.
     * @param accountNumber The account number to search for.
     * @return An Optional containing the account if found, or empty otherwise.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findForUpdateByUserAndAccountNumber(User user, String accountNumber);

    /**
     * Deletes an account by its account number with a pessimistic write lock.
     *
     * @param accountNumber The account number of the account to delete.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    void deleteByAccountNumber(String accountNumber);
}