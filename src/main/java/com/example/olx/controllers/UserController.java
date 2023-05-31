package com.example.olx.controllers;


import com.example.olx.security.authUtils.AuthenticationRequest;
import com.example.olx.security.authUtils.AuthenticationResponse;
import com.example.olx.security.authUtils.RegisterRequest;
import com.example.olx.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(RegisterRequest request, Model model) {
        model.addAttribute("jwtToken",  "Bearer " + userService.register(request).getToken());
        return "redirect:/hellopage";
    }

    @GetMapping("/registration")
    public String registerView() {
        return "registration";
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authentication( @RequestBody AuthenticationRequest authRequest ) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }
}
