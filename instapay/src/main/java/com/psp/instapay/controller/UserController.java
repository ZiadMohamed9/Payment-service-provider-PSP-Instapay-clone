package com.psp.instapay.controller;

import com.psp.instapay.model.service.UserService;
import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.common.exception.InvalidRoleException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.model.dto.request.UpdateRoleRequest;
import com.psp.instapay.model.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers() {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Success!")
                        .data(userService.getAllUsers())
                        .build()
        );
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("Success!")
                        .data(user)
                        .build()
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update_role")
    public ResponseEntity<ApiResponse> updateUserRole(@Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .status(HttpStatus.OK)
                        .message("User role updated successfully!")
                        .data(userService.updateUserRole(request.getUsername(), request.getRole()))
                        .build()
        );
    }
}
