package com.psp.instapay.common.mapper;

import com.psp.instapay.model.dto.UserDTO;
import com.psp.instapay.model.entity.User;
import com.psp.instapay.model.dto.request.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toUserDTO(User user);

    User toUser(SignUpRequest request);
}
