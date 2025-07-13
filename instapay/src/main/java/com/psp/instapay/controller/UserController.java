package com.psp.instapay.controller;

import com.psp.instapay.model.dto.response.ResponseDto;
import com.psp.instapay.model.service.UserService;
import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.model.dto.request.UpdateRoleRequest;
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
     * @return a ResponseEntity containing an ResponseDto with the list of all users
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAllUsers() {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("Success!")
                        .data(userService.getAllUsers())
                        .build()
        );
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     * This endpoint is accessible to all authenticated users.
     *
     * @return a ResponseEntity containing an ResponseDto with the user's profile details
     */
    @GetMapping("/profile")
    public ResponseEntity<ResponseDto> getUserByUsername() {
        UserDTO user = userService.getUserProfile();
        return ResponseEntity.ok(
                ResponseDto.builder()
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
     * @return a ResponseEntity containing an ResponseDto with the updated user details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update_role")
    public ResponseEntity<ResponseDto> updateUserRole(@Valid @RequestBody UpdateRoleRequest request) {
        return ResponseEntity.ok(
                ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("User role updated successfully!")
                        .data(userService.updateUserRole(request.getUsername(), request.getRole()))
                        .build()
        );
    }
}