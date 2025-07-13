package com.psp.instapay.controller;

import com.psp.instapay.model.dto.response.ResponseDto;
import com.psp.instapay.model.dto.request.LoginRequest;
import com.psp.instapay.model.dto.request.SignUpRequest;
import com.psp.instapay.model.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing authentication-related operations.
 * Provides endpoints for user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Registers a new user.
     *
     * @param request the SignUpRequest containing user registration details
     * @return a ResponseEntity containing an ResponseDto with the registration status and user details
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto
                        .builder()
                        .status(HttpStatus.CREATED)
                        .message("User Registered Successfully")
                        .data(authenticationService.register(request))
                        .build()
                );
    }

    /**
     * Authenticates a user and logs them in.
     *
     * @param request the LoginRequest containing user login credentials
     * @return a ResponseEntity containing an ResponseDto with the login status and user details
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ResponseDto
                        .builder()
                        .status(HttpStatus.OK)
                        .message("User Logged In Successfully")
                        .data(authenticationService.login(request))
                        .build()
        );
    }
}