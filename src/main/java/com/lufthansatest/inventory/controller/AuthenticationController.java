package com.lufthansatest.inventory.controller;

import com.lufthansatest.inventory.model.dto.AuthenticationRequest;
import com.lufthansatest.inventory.model.dto.AuthenticationResponse;
import com.lufthansatest.inventory.model.dto.UserDTO;
import com.lufthansatest.inventory.service.AuthenticationService;
import com.lufthansatest.inventory.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST,path = "/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user){
        return ResponseEntity.ok(userService.register(user));
    }
}