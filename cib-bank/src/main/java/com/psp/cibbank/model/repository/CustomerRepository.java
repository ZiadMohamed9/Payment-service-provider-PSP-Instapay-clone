package com.psp.cibbank.model.repository;

import com.psp.cibbank.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Customer entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Finds a customer by their phone number.
     *
     * @param phoneNumber the phone number of the customer
     * @return an Optional containing the Customer if found, or empty if not found
     */
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    /**
     * Checks if a customer exists by their phone number.
     *
     * @param phoneNumber the phone number to check
     * @return true if a customer with the given phone number exists, false otherwise
     */
    boolean existsByPhoneNumber(String phoneNumber);
}