package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationExistException;
import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.model.request.LoginRequest;
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

@Service
public class UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private JWTUtils jwtUtils;

    private AuthenticationManager authenticationManager;
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
     * @param registerRequest the object that contains the email and password
     * @return created user
     * @throws InformationExistException if the email is already existed
     * @throws InformationInvalidException if the password is blank
     */
    public User createUser(RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findUserByEmailAddress(registerRequest.getEmail());
        if (user.isPresent()) {
            throw new InformationExistException("The email address " + registerRequest.getEmail() + " is already existed");
        } else {
            if (registerRequest.getPassword().isBlank()) {
                throw new InformationInvalidException("Password can not be empty or only contains space character");
            } else {
                User newUser = new User(null,registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
                return userRepository.save(newUser);
            }
        }
    }

    /**
     * find a user by an email address
     * @param email the email dress used to find the user
     * @return found user
     */
    public Optional<User> findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    /**
     * authenticate a user by email and password
     * @param loginRequest contains the email and password of the user
     * @return JWT
     */
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT, myUserDetails.getUser().getTweets(), myUserDetails.getUser().getProfile()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new LoginResponse("Error: Username or password is incorrect", null, null));
        }
    }
}
