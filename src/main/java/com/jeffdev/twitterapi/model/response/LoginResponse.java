package com.jeffdev.twitterapi.model.response;

import com.jeffdev.twitterapi.model.Profile;
import com.jeffdev.twitterapi.model.Tweet;

import java.util.List;

/**
 * A simple model class representing a response object for a login operation.
 */
public class LoginResponse {
    private String token;
    private List<Tweet> tweets;
    private Profile profile;

    /**
     * Creates a new instance of LoginResponse with the token, a list of tweets and a user's profile.
     *
     * @param token The JWT
     * @param tweets User's tweets
     * @param profile User's profile
     */
    public LoginResponse(String token, List<Tweet> tweets, Profile profile) {
        this.token = token;
        this.tweets = tweets;
        this.profile = profile;
    }

    /**
     * Returns the token.
     *
     * @return The token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Return user's tweets
     * @return a list of tweets
     */
    public List<Tweet> getTweets() {
        return tweets;
    }

    /**
     * Return user's profile
     * @return a user's profile
     */
    public Profile getProfile() {return profile;}
}

