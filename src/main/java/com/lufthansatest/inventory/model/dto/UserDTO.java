package com.lufthansatest.inventory.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lufthansatest.inventory.model.entity.User;
import com.lufthansatest.inventory.model.enums.UserRole;

@JsonSerialize
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private UserRole role;

    public UserDTO(Long id, String username, String password, String email, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Constructor to map fields from User entity
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public UserDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
