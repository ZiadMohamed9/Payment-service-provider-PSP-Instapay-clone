package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for sign-up request.
 * Represents the request payload for user registration.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    /**
     * The full name of the user signing up.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Name is required")
    private String name;

    /**
     * The username chosen by the user for the account.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Username is required")
    private String username;

    /**
     * The phone number of the user signing up.
     * Must be exactly 11 digits long and cannot be null.
     */
    @NotNull(message = "Phone number is required")
    @Size(min = 11, max = 11, message = "Phone number should be 11 digits")
    private String phoneNumber;

    /**
     * The password chosen by the user for the account.
     * Must be between 8 and 32 characters long and cannot be null.
     */
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password size should be between 8 and 32 characters")
    private String password;
}