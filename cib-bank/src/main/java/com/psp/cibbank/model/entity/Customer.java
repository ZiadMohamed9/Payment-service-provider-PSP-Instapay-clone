package com.psp.cibbank.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity class representing a customer in the CIB Bank system.
 * A customer has a name and a unique phone number.
 * It also tracks the creation timestamp.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Customer {

    /**
     * Unique identifier for the customer
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Full name of the customer
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Unique phone number of the customer (11 digits)
     */
    @Column(name = "phone_number", nullable = false, unique = true, length = 11)
    private String phoneNumber;

    /**
     * Timestamp when the customer was created
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Constructor to create a new customer with specified details
     *
     * @param name The full name of the customer
     * @param phoneNumber The unique phone number of the customer
     */
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets the creation timestamp before persisting the entity
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
