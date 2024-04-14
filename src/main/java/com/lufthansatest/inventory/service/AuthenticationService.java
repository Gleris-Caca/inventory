package com.lufthansatest.inventory.service;

import com.lufthansatest.inventory.model.dto.AuthenticationRequest;
import com.lufthansatest.inventory.model.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);

}