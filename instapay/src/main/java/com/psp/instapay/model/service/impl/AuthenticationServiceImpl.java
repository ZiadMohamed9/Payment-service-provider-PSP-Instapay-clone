package com.psp.instapay.model.service.impl;

import com.psp.instapay.model.enums.Role;
import com.psp.instapay.common.exception.AccountAlreadyExistsException;
import com.psp.instapay.model.dto.request.LoginRequest;
import com.psp.instapay.model.dto.request.SignUpRequest;
import com.psp.instapay.model.dto.response.AuthenticationResponse;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.common.mapper.UserMapper;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.security.jwt.JwtService;
import com.psp.instapay.model.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public AuthenticationResponse register(SignUpRequest request) {
        if (isUsernameOrPhoneAlreadyExists(request.getUsername(), request.getPhoneNumber())) {
            throw new AccountAlreadyExistsException("Username or phone number already exists");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getUsername()));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    private boolean isUsernameOrPhoneAlreadyExists(String email, String phoneNumber) {
        return userRepository.existsByUsername(email) || userRepository.existsByPhoneNumber(phoneNumber);
    }
}
