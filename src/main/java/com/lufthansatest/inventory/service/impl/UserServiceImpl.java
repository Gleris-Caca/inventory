package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.exceptions.GeneralException;
import com.lufthansatest.inventory.mapper.UserMapper;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.repository.UserRepository;
import com.lufthansatest.inventory.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    //private final InventoryItemRepository inventoryItemRepository;
    //private final OrderRepository orderRepository;
    //private final TruckRepository truckRepository;
    private final PasswordEncoder encoder;


    @Override
    public UserDTO createUser(User user) {
        log.info("Create new user{}",user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return userMapper.toDto(user);
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
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO loadUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        checkIfUserExist(user);
        return userMapper.toDto(user.get());
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
        Optional<User> existingUser = userRepository.findById(id);
        checkIfUserExist(existingUser);
        User user = existingUser.get();
        log.info("Updating user{}",user.getUsername());
        if(requestedUser.getUsername() != null) {
            if(userRepository.findByUsername(requestedUser.getUsername()).isPresent()) {
                throw new GeneralException("Username is taken!");
            }
            user.setUsername(requestedUser.getUsername());
        }
        user.setPassword(existingUser.get().getPassword());
        if (requestedUser.getUsername() != null) {
            user.setUsername(requestedUser.getUsername());}
        if (requestedUser.getPassword() != null){
            user.setPassword(requestedUser.getPassword());}
        if (requestedUser.getRole() != null) {
            user.setRole(requestedUser.getRole());}
        if (requestedUser.getEmail() != null) {
            user.setEmail(requestedUser.getEmail());}
        user = userRepository.save(user);
        return userMapper.toDto(user);

    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Deleting User with id{} ",id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }else throw new GeneralException("User not found with id " + id);
    }

}