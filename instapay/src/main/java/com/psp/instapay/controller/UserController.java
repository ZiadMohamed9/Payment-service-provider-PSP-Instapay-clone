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

/**
 * REST controller for managing user-related operations.
 * Provides endpoints for retrieving user details and updating user roles.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Retrieves all users.
     * This endpoint is restricted to users with the 'ADMIN' authority.
     *
     * @return a ResponseEntity containing an ApiResponse with the list of all users
     */
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

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return a ResponseEntity containing an ApiResponse with the user details
     */
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

    /**
     * Updates the role of a user.
     * This endpoint is restricted to users with the 'ADMIN' authority.
     *
     * @param request the UpdateRoleRequest containing the username and the new role
     * @return a ResponseEntity containing an ApiResponse with the updated user details
     */
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