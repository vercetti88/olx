package com.example.olx.controllers;


import com.example.olx.security.authUtils.AuthenticationRequest;
import com.example.olx.security.authUtils.AuthenticationResponse;
import com.example.olx.security.authUtils.RegisterRequest;
import com.example.olx.services.UserService;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<AuthenticationResponse> registration(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authentication( @RequestBody AuthenticationRequest authRequest ) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }
}
