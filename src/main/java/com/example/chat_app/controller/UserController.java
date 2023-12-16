package com.example.chat_app.controller;

import com.example.chat_app.model.User;
import com.example.chat_app.requests.UserLoginRequest;
import com.example.chat_app.requests.UserRegistrationRequest;
import com.example.chat_app.service.UserService;
import com.example.chat_app.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        System.out.println(userRegistrationRequest);
        // Perform user registration
        User registeredUser = userService.registerUser(userRegistrationRequest);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        // Perform user login
        User loggedInUser = userService.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword());

        if (loggedInUser != null) {
            // Successful login
            return ResponseEntity.ok(loggedInUser);
        } else {
            // Invalid login, return an appropriate response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
