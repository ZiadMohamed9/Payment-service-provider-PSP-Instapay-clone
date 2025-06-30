package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for login request.
 * Represents the request payload for user login.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The username of the user attempting to log in.
     * This field is required and cannot be null.
     */
    @NotNull(message = "Username is required")
    private String username;

    /**
     * The password of the user attempting to log in.
     * This field is required and must be between 8 and 32 characters long.
     */
    @NotNull(message = "Password is required")
    @Size(min = 8, max = 32, message = "Password size should be between 8 and 32 characters")
    private String password;
}