package com.psp.instapay.model.entity;

import com.psp.instapay.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entity class representing a user in the system.
 * Implements the UserDetails interface for Spring Security integration.
 * Maps to the "user" table in the database.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements UserDetails {

    /**
     * The unique identifier for the user.
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    /**
     * The full name of the user.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The unique username of the user.
     * Used for authentication and identification.
     * Cannot be null and limited to 20 characters.
     */
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    /**
     * The unique phone number of the user.
     * Cannot be null and limited to 11 characters.
     */
    @Column(nullable = false, unique = true, length = 11)
    private String phoneNumber;

    /**
     * The password of the user.
     * Cannot be null.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The role of the user.
     * Indicates the user's permissions and access level.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * The list of accounts associated with the user.
     * Represents a one-to-many relationship with the Account entity.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts;

    /**
     * The date and time when the user was created.
     * Automatically set when the user is persisted.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time when the user was last updated.
     * Automatically set when the user is updated.
     */
    @Column()
    private LocalDateTime updatedAt;

    /**
     * Constructor to create a user with the specified details.
     *
     * @param name The full name of the user.
     * @param username The unique username of the user.
     * @param phoneNumber The unique phone number of the user.
     * @param password The password of the user.
     * @param role The role of the user.
     */
    public User(String name, String username, String phoneNumber, String password, Role role) {
        this.name = name;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

    /**
     * Sets the createdAt field to the current date and time before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Sets the updatedAt field to the current date and time before updating the entity.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Returns the authorities granted to the user.
     * Based on the user's role.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The user's password.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return The user's username.
     */
    @Override
    public String getUsername() {
        return this.username;
    }
}