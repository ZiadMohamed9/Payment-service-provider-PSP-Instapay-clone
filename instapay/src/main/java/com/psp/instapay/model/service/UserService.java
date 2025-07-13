package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Service interface for managing user-related operations.
 * Extends UserDetailsService to integrate with Spring Security for user authentication.
 */
public interface UserService extends UserDetailsService {

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return The user details as a UserDTO.
     */
    UserDTO getUserProfile();

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user details as a UserDTO.
     */
    UserDTO getUserById(Long id);

    /**
     * Retrieves a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to retrieve.
     * @return The user details as a UserDTO.
     */
    UserDTO getUserByPhoneNumber(String phoneNumber);

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users as UserDTOs.
     */
    List<UserDTO> getAllUsers();

    /**
     * Updates the role of a user identified by their username.
     *
     * @param username The username of the user whose role is to be updated.
     * @param role The new role to assign to the user.
     * @return The updated user details as a UserDTO.
     */
    UserDTO updateUserRole(String username, String role);
}