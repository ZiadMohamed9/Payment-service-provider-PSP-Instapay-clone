package com.psp.instapay.controller;

import com.psp.instapay.common.exception.AccountAlreadyExistsException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.model.dto.response.ApiResponse;
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

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse
                        .builder()
                        .status(HttpStatus.CREATED)
                        .message("User Registered Successfully")
                        .data(authenticationService.register(request))
                        .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(
                ApiResponse
                        .builder()
                        .status(HttpStatus.OK)
                        .message("User Logged In Successfully")
                        .data(authenticationService.login(request))
                        .build()
        );
    }
}