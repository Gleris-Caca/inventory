package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
@Schema
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @RequestMapping(method = RequestMethod.GET, path = "/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.loadAllUsers());
        }catch (Exception e){
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,e.getLocalizedMessage())).build();
        }
    }


    @PreAuthorize("hasAnyAuthority('SYSTEM_ADMIN')")
    @PostMapping("/createUser")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PreAuthorize(value = "hasAnyRole('SYSTEM_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, value = "/updateUser/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(value = "id") Long id,@RequestBody User user){
        userService.updateUser(id, user);
        return ResponseEntity.status(200).build();
    }

    @PreAuthorize(value = "hasAnyRole('SYSTEM_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}