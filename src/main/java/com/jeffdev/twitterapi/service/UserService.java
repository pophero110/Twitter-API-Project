package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationExistException;
import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * create a user with a unique email and non-blank password
     *
     * @param userObject the object that contains the email and password
     * @return created user
     * @throws InformationExistException
     * @throws InformationInvalidException
     */
    public User createUser(User userObject) {
        Optional<User> user = userRepository.findUserByEmailAddress(userObject.getEmailAddress());
        if (user.isPresent()) {
            throw new InformationExistException("The email address " + userObject.getEmailAddress() + " is already existed");
        } else {
            if (userObject.getPassword().isBlank()) {
                throw new InformationInvalidException("Password can not be empty or only contains space character");
            } else {
                return userRepository.save(userObject);
            }
        }
    }
}
