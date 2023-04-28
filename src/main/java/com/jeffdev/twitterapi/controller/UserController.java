package com.jeffdev.twitterapi.controller;

import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param userObject the User object to create
     * @return the created User object with its unique ID set
     */
    @PostMapping("")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }
}
