package com.psp.instapay.model.service;

import com.psp.instapay.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDTO getUserByUsername(String username);

    UserDTO getUserById(Long id);

    UserDTO getUserByPhoneNumber(String phoneNumber);

    List<UserDTO> getAllUsers();

    UserDTO updateUserRole(String username, String role);
}
