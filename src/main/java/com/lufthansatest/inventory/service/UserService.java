package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {


    UserDTO createUser(User user);

    UserDTO register(UserDTO userDTO);
    List<UserDTO> loadAllUsers();
    UserDTO updateUser(Long id, User requestedUser);
    void deleteUser(Long id);
}

