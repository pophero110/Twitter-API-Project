package com.jeffdev.twitterapi.model.response;

import com.jeffdev.twitterapi.model.Profile;

/**
 * Represents a response object that is returned after a successful login operation.
 * Contains a JWT token, a list of the user's tweets, and the user's profile information.
 */
public record LoginResponse(String token, Profile profile) {
}