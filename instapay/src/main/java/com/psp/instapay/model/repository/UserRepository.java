package com.psp.instapay.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.psp.instapay.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities.
 * Extends JpaRepository to provide CRUD operations and custom queries.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to search for.
     * @return An Optional containing the user if found, or empty otherwise.
     */
    Optional<User> findByPhoneNumber(String phoneNumber);

    /**
     * Checks if a user exists by their username.
     *
     * @param username The username to check.
     * @return True if a user with the given username exists, false otherwise.
     */
    boolean existsByUsername(String username);

    /**
     * Checks if a user exists by their phone number.
     *
     * @param phoneNumber The phone number to check.
     * @return True if a user with the given phone number exists, false otherwise.
     */
    boolean existsByPhoneNumber(String phoneNumber);
}