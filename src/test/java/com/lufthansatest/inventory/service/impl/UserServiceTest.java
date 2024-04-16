package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.GeneralException;
import com.lufthansatest.inventory.mapper.UserMapper;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.model.enums.UserRole;
import com.lufthansatest.inventory.repository.UserRepository;
import com.lufthansatest.inventory.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


}
