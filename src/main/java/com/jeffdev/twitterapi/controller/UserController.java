package com.jeffdev.twitterapi.controller;

import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.model.request.LoginRequest;
import com.jeffdev.twitterapi.model.request.RegisterRequest;
import com.jeffdev.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user in the database
     *
     * @param registerRequest the object that contains the email and password
     * @return the created User object with its unique ID set
     */
    @PostMapping("")
    public User createUser(@RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }
}
