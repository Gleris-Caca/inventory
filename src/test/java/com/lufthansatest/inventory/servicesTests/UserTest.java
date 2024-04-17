package com.lufthansatest.inventory.servicesTests;

import com.lufthansatest.inventory.mapper.UserMapper;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.model.enums.UserRole;
import com.lufthansatest.inventory.repository.UserRepository;
import com.lufthansatest.inventory.service.UserService;
import com.lufthansatest.inventory.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private  UserMapper userMapper;

    @Mock
    private  PasswordEncoder encoder;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Mock
    private Authentication authentication;

    private UserService userService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        userService =  new UserServiceImpl(userRepository,userMapper,encoder);
    }

    @Test
    void findAllUsers(){
        User user = new User();
        user.setRole(UserRole.SYSTEM_ADMIN);
        user.setId(1L);
        user.setEmail("fake@gmail.com");
        user.setUsername("test_unitTest");

        User user1 = new User();
        user1.setRole(UserRole.WAREHOUSE_MANAGER);
        user1.setId(1L);
        user1.setEmail("fake@gmail.com");
        user1.setUsername("test_unitTest");

        Mockito.doReturn(userRepository.findAll()).doReturn(List.of(user,user1));
    }

    @Test
    void testRegisterUser(){
        User user = new User();
        user.setRole(UserRole.SYSTEM_ADMIN);
        user.setId(1L);
        user.setEmail("fake@gmail.com");
        user.setUsername("test_unitTest");
        user.setPassword("11111111");

        String encodedPassword = "encodedPassword";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setId(user.getId());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());

        Mockito.when(encoder.encode(user.getPassword())).thenReturn(encodedPassword);
        when(userMapper.toDto(user)).thenReturn(userDTO);
        Mockito.doReturn(user).when(userRepository).save(any());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaims()).thenReturn(Collections.singletonMap("scope", Collections.singletonList(UserRole.SYSTEM_ADMIN.name())));

        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Mockito.doReturn(user).when(userRepository).save(any());

        UserDTO createdUser = userService.createUser(user);

        Mockito.verify(userRepository,Mockito.times(1))
                .save(userArgumentCaptor.capture());

        final User savedUser = userArgumentCaptor.getValue();
        Assertions.assertEquals(user,savedUser);
        Assertions.assertEquals(user.getId(),savedUser.getId());
        Assertions.assertNotNull(createdUser);
    }

}
