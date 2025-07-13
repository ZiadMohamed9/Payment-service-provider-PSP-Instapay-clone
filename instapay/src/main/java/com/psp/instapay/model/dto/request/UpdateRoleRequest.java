package com.psp.instapay.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for updating a user's role.
 * Represents the request payload for changing the role of a user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequest {

    /**
     * The username of the user whose role is being updated.
     * This field is required and cannot be null.
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * The new role to be assigned to the user.
     * This field is required and cannot be null.
     */
    @NotBlank(message = "Role is required")
    private String role;
}