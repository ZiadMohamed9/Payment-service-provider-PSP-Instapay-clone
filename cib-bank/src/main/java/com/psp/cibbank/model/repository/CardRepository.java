package com.psp.cibbank.model.repository;

import com.psp.cibbank.model.entity.Card;
import com.psp.cibbank.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query(
            "SELECT c FROM Card c WHERE c.customer = :customer AND c.cardNumber = :cardNumber AND c.pin = :pin"
    )
    Optional<Card> findByCustomerAndCardNumberAndPin(Customer customer, String cardNumber, String pin);

}
