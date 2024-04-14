package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)//retrieves a user  based on the provided email
                .map(this::toUserDetails) //convert the User entity to a UserDetails object
                .orElseThrow(()-> new UsernameNotFoundException("Email not found"));
    }



    private UserDetails toUserDetails(com.lufthansatest.inventory.model.entity.User user){
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()).build();
    }


}