package com.psp.instapay.model.service.impl;

import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.enums.Role;
import com.psp.instapay.model.repository.UserRepository;
import com.psp.instapay.model.service.UserService;
import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.common.exception.InvalidRoleException;
import com.psp.instapay.common.exception.UserNotFoundException;
import com.psp.instapay.common.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserDTO getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with phone number: " + phoneNumber));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDTO)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
