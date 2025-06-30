package com.psp.instapay.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user details.
 * Represents the user information including name and username.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    /**
     * The full name of the user.
     */
    private String name;

    /**
     * The unique username of the user.
     * Used for user identification and authentication.
     */
    private String username;
}