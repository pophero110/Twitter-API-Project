package com.jeffdev.twitterapi.model.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Represents a request to register or login, containing the user's email address and password.
 */
public class UserRequest {
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank
    private String password;

    /**
     * Returns the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }
}
