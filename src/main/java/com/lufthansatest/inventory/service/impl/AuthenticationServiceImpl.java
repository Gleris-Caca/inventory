package com.lufthansatest.inventory.service.impl;

import com.lufthansatest.inventory.model.dto.AuthenticationRequest;
import com.lufthansatest.inventory.model.dto.AuthenticationResponse;
import com.lufthansatest.inventory.service.AuthenticationService;
import com.lufthansatest.inventory.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;



    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException {
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        authenticationResponse.setToken(tokenService.generateToken(userDetailsService.loadUserByUsername(authenticationRequest.getEmail())));
        return authenticationResponse;
    }
}