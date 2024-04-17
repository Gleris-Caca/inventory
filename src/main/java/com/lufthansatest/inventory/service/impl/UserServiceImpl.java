package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.GeneralException;
import com.lufthansatest.inventory.mapper.UserMapper;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.model.enums.UserRole;
import com.lufthansatest.inventory.repository.UserRepository;
import com.lufthansatest.inventory.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;


    @Override
    public UserDTO createUser(User user) {
        log.info("Create new user{}",user.getUsername());
        if (isAdmin()) {
            user.setPassword(encoder.encode(user.getPassword()));
            user = userRepository.save(user);
            return userMapper.toDto(user);
        }else {
            throw new GeneralException("The user does not have the rights.");
        }
    }

    @Override
    public UserDTO register(UserDTO userDTO) {
        if (userRepository.findByUsername(userDTO.getUsername()).isPresent()) {
            throw new GeneralException("Username already exists");
        }
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new GeneralException("Email already exists");
        }

        log.info("Creating new user");
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }


    @Override
    public List<UserDTO> loadAllUsers() {
        if (isAdmin()) {
            return  userRepository
                    .findAll()
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        }
        else {
            return (List<UserDTO>) ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED)).build();
        }
    }


    private void checkIfUserExist(Optional<User> user) {
        if (user.isEmpty() || !user.get().isEnabled()) {
            if (user.isEmpty()) //is empty user was not found
                log.error("User not found");
            else // is not empty but the user is not enabled
                log.error("User is not enabled");
            throw new GeneralException("User not found");
        }
    }

    @Override
    public UserDTO updateUser(Long id, User requestedUser) {
        // Retrieve existing user from the database
        Optional<User> existingUserOptional = userRepository.findById(id);
        User existingUser = existingUserOptional.orElseThrow(() -> new GeneralException("User not found"));

        Long userID = existingUser.getId();


            // Update user fields if they are not null in the requestedUser object
            if (requestedUser.getUsername() != null) {
                existingUser.setUsername(requestedUser.getUsername());
            }
            if (requestedUser.getPassword() != null) {
                existingUser.setPassword(requestedUser.getPassword());
            }
            if (requestedUser.getRole() != null) {
                existingUser.setRole(requestedUser.getRole());
            }
            if (requestedUser.getEmail() != null) {
                existingUser.setEmail(requestedUser.getEmail());
            }


        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }
    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting User with id{} ",id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }else throw new GeneralException("User not found with id " + id);
    }

    public boolean isAdmin(){
        JwtAuthenticationToken authentication = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        Object objectRoles = ((Jwt) authentication.getCredentials()).getClaims().getOrDefault("scope", new ArrayList<>());
        List<String> roles = (List<String>) objectRoles;
            return roles.contains(UserRole.SYSTEM_ADMIN.name());
    }

    private boolean isManager(){
        JwtAuthenticationToken authentication = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        Object objectRoles = ((Jwt) authentication.getCredentials()).getClaims().getOrDefault("scope", new ArrayList<>());
        List<String> roles = (List<String>) objectRoles;
        return roles.contains(UserRole.WAREHOUSE_MANAGER.name());
    }

    private boolean isClient(){
        JwtAuthenticationToken authentication = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication());
        Object objectRoles = ((Jwt) authentication.getCredentials()).getClaims().getOrDefault("scope", new ArrayList<>());
        List<String> roles = (List<String>) objectRoles;
        return roles.contains(UserRole.CLIENT.name());
    }

}