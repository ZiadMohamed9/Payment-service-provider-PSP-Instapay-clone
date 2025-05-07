package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.request.LoginRequest;
import com.psp.instapay.model.dto.request.SignUpRequest;
import com.psp.instapay.model.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(SignUpRequest request);

    AuthenticationResponse login(LoginRequest request);
}
