package com.psp.instapay.model.service.impl;

import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.enums.Role;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.UserService;
import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.exception.InvalidRoleException;
import com.psp.instapay.exception.UserNotFoundException;
import com.psp.instapay.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the UserService interface.
 * Provides methods for managing user-related operations, such as retrieving user details and updating roles.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user details as a UserDTO.
     * @throws UserNotFoundException If the user is not found with the given ID.
     */
    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    /**
     * Retrieves a user by their phone number.
     *
     * @param phoneNumber The phone number of the user to retrieve.
     * @return The user details as a UserDTO.
     * @throws UserNotFoundException If the user is not found with the given phone number.
     */
    @Override
    public UserDTO getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
    }

    /**
    * Retrieves the profile of the currently authenticated user.
    *
    * @return The user details as a UserDTO.
    * @throws UserNotFoundException If the authenticated user is not found.
    */
    @Override
    public UserDTO getUserProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of all users as UserDTOs.
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    /**
     * Updates the role of a user identified by their username.
     *
     * @param username The username of the user whose role is to be updated.
     * @param role The new role to assign to the user (e.g., USER or ADMIN).
     * @return The updated user details as a UserDTO.
     * @throws UserNotFoundException If the user is not found with the given username.
     * @throws InvalidRoleException If the provided role is invalid.
     */
    @Override
    public UserDTO updateUserRole(String username, String role) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        if (!role.equalsIgnoreCase("USER") && !role.equalsIgnoreCase("ADMIN"))
            throw new InvalidRoleException("Invalid role: " + role);

        user.setRole(Role.valueOf(role.toUpperCase()));
        userRepository.save(user);

        return userMapper.toUserDTO(user);
    }

    /**
     * Loads a user by their username for authentication purposes.
     *
     * @param username The username of the user to load.
     * @return The user details as a UserDetails object.
     * @throws UsernameNotFoundException If the user is not found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}