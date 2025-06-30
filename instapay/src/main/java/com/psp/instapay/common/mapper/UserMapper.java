package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.dto.request.SignUpRequest;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between User entity, UserDTO, and SignUpRequest.
 * Utilizes MapStruct for automatic mapping.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps a User entity to a UserDTO.
     *
     * @param user the User entity to be mapped
     * @return the mapped UserDTO
     */
    UserDTO toUserDTO(User user);

    /**
     * Maps a SignUpRequest to a User entity.
     *
     * @param request the SignUpRequest containing user details
     * @return the mapped User entity
     */
    User toUser(SignUpRequest request);
}