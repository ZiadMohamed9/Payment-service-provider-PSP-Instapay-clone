package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.request.LoginRequest;
import com.psp.instapay.model.dto.request.SignUpRequest;
import com.psp.instapay.model.dto.response.AuthenticationResponse;

/**
 * Service interface for managing authentication operations.
 * Provides methods for user registration and login.
 */
public interface AuthenticationService {

    /**
     * Registers a new user in the system.
     *
     * @param request The sign-up request containing user details.
     * @return An AuthenticationResponse containing authentication details for the registered user.
     */
    AuthenticationResponse register(SignUpRequest request);

    /**
     * Authenticates a user and generates an authentication response.
     *
     * @param request The login request containing user credentials.
     * @return An AuthenticationResponse containing authentication details for the logged-in user.
     */
    AuthenticationResponse login(LoginRequest request);
}