package com.jeffdev.twitterapi.model.response;

import com.jeffdev.twitterapi.model.Tweet;

import java.util.List;

/**
 * A simple model class representing a response object for a login operation.
 */
public class LoginResponse {
    private String token;
    private List<Tweet> tweets;

    /**
     * Creates a new instance of LoginResponse with the specified message.
     *
     * @param token The response message.
     */
    public LoginResponse(String token, List<Tweet> tweets) {
        this.token = token;
        this.tweets = tweets;
    }

    /**
     * Returns the response message.
     *
     * @return The response message.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the response message.
     *
     * @param token The new response message.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Return user's tweets
     * @return a list of tweets
     */
    public List<Tweet> getTweets() {
        return tweets;
    }
}

