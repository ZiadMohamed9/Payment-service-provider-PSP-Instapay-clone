package com.psp.instapay.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a bank in the system.
 * Maps to the "bank" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {

    /**
     * The unique identifier for the bank.
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the bank.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Constructor to create a bank with the specified name.
     *
     * @param name The name of the bank.
     */
    public Bank(String name) {
        this.name = name;
    }
}