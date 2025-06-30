package com.psp.instapay.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for authentication response.
 * Represents the response payload containing the authentication token.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * The authentication token issued upon successful login.
     * Used to authenticate subsequent API requests.
     */
    private String token;
}