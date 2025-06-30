package com.psp.nbebank.model.repository;

import com.psp.nbebank.model.entity.Card;
import com.psp.nbebank.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Card entities in the CIB Bank system.
 * Provides methods for querying and manipulating card data, including
 * authentication-related queries.
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    /**
     * Finds a card by customer, card number, and PIN for authentication purposes.
     * This method is used during card verification to ensure that the provided
     * credentials match a valid card in the system.
     *
     * @param customer The customer who owns the card
     * @param cardNumber The card number to match
     * @param pin The PIN to verify
     * @return An Optional containing the card if found and authenticated, or empty if not found or authentication fails
     */
    @Query(
            "SELECT c FROM Card c WHERE c.customer = :customer AND c.cardNumber = :cardNumber AND c.pin = :pin"
    )
    Optional<Card> findByCustomerAndCardNumberAndPin(Customer customer, String cardNumber, String pin);
}