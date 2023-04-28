package com.jeffdev.twitterapi.model.response;

/**
 * A simple model class representing a response object for a login operation.
 */
public class LoginResponse {
    private String message;

    /**
     * Creates a new instance of LoginResponse with the specified message.
     *
     * @param message The response message.
     */
    public LoginResponse(String message) {
        this.message = message;
    }

    /**
     * Returns the response message.
     *
     * @return The response message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the response message.
     *
     * @param message The new response message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

