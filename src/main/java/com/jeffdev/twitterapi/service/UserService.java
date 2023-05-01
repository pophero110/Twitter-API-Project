package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationExistException;
import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.model.request.UserRequest;
import com.jeffdev.twitterapi.model.response.LoginResponse;
import com.jeffdev.twitterapi.repository.UserRepository;
import com.jeffdev.twitterapi.security.JWTUtils;
import com.jeffdev.twitterapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class that handles user-related operations.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager,
                       @Lazy MyUserDetails myUserDetails) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    /**
     * create a user with a unique email and non-blank password
     *
     * @param userRequest the object that contains the email and password
     * @return created user
     * @throws InformationExistException   if the email is already existed
     * @throws InformationInvalidException if the password is blank
     */
    public User createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findUserByEmailAddress(userRequest.getEmail());
        if (user.isPresent()) {
            throw new InformationExistException("The email address " + userRequest.getEmail() + " is already existed");
        } else {
            if (userRequest.getPassword().isBlank()) {
                throw new InformationInvalidException("Password can not be empty or only contains space character");
            } else {
                User newUser = new User(null, userRequest.getEmail(), passwordEncoder.encode(userRequest.getPassword()));
                return userRepository.save(newUser);
            }
        }
    }

    /**
     * find a user by an email address
     *
     * @param email the email dress used to find the user
     * @return found user
     */
    public Optional<User> findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    /**
     * authenticate a user by email and password
     *
     * @param userRequest contains the email and password of the user
     * @return JWT
     */
    public ResponseEntity<?> loginUser(UserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            // ResponseEntity represents the whole HTTP response: status code, headers, and body
            // https://www.baeldung.com/spring-response-entity
            return ResponseEntity.ok(new LoginResponse(JWT, myUserDetails.getUser().getProfile()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponse("Error: Username or password is incorrect", null));
        }
    }
}
