package com.psp.instapay.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "service_url", nullable = false)
    private String serviceUrl;

    public Bank(String name, String serviceUrl) {
        this.name = name;
        this.serviceUrl = serviceUrl;
    }
} 