package com.lufthansatest.inventory.mapper;

import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j

public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    public User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());
        return user;
    }

    @Override
    public UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }

        UserDTO userDto = new UserDTO();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setRole(entity.getRole());
        return userDto;
    }
}