package com.jeffdev.twitterapi.model.request;

/**
 * A simple model class representing a request object for a login operation.
 */
public class LoginRequest {
    private String email;
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
