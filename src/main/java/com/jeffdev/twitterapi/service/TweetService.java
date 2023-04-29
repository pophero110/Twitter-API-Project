package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.exception.InformationNotFoundException;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.repository.TweetRepository;
import com.jeffdev.twitterapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private TweetRepository tweetRepository;

    @Autowired
    public void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public static User getCurrentLoggedInUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }

    /**
     * Create a tweet in database and return it
     *
     * @param tweetObject The tweet object that need to saved in database
     * @return The newly saved tweet
     * @throws InformationInvalidException If content is blank in the tweetObject
     */
    public Tweet createTweet(Tweet tweetObject) {
        try {
            if (tweetObject.getContent().isBlank()) {
                throw new InformationInvalidException("Content can not be empty or contains only space character");
            } else if (tweetObject.getContent().length() > 280) {
                throw new InformationInvalidException("Content can not be longer than 280 character");
            } else {
                tweetObject.setUser(getCurrentLoggedInUser());
                return tweetRepository.save(tweetObject);
            }
        } catch (Exception e) {
            throw new InformationInvalidException(e.getMessage());
        }
    }

    /**
     * Get a list of tweets sorted by created_at desc
     *
     * @return a list of tweets or an empty list
     */
    public List<Tweet> getTweets() {
        return tweetRepository.findAllByOrderByCreatedAtDesc();
    }


    /**
     * update a specific tweet by tweet id based on the tweet object
     *
     * @param tweetId the id of the tweet that needs to be updated based on the tweet object
     * @return updated tweet
     * @throws InformationNotFoundException If no tweet associated with given tweet id
     */
    public Tweet updateTweet(Long tweetId, Tweet tweetObject) {
        Optional<Tweet> tweet = tweetRepository.findByIdAndUserId(tweetId, getCurrentLoggedInUser().getId());
        if (tweet.isPresent()) {
            if (tweetObject.getContent().isBlank()) {
                throw new InformationInvalidException("Content can not be empty or contains only space character");
            } else if (tweetObject.getContent().length() > 280) {
                throw new InformationInvalidException("Content can not be longer than 280 character");
            } else {
                tweet.get().setContent(tweetObject.getContent());
                return tweetRepository.save(tweet.get());
            }
        } else {
            throw new InformationNotFoundException("Tweet is not found with id " + tweetId);
        }
    }

    /**
     * delete a tweet based on tweet id
     *
     * @param tweetId the id of the tweet
     * @return deleted tweet
     * @throws InformationNotFoundException If no tweet associated with given tweet id
     */
    public Tweet deleteTweet(Long tweetId) {
        Optional<Tweet> tweet = tweetRepository.findByIdAndUserId(tweetId, getCurrentLoggedInUser().getId());
        if (tweet.isPresent()) {
            tweetRepository.delete(tweet.get());
            return tweet.get();
        } else {
            throw new InformationNotFoundException("Tweet is not found with id " + tweetId);
        }
    }

    /**
     * get a tweet by given tweet id
     * If a tweet can not be found by given tweet id, an InformationNotFoundException will be thrown
     *
     * @param tweetId the given tweet id
     * @return found tweet
     * @throws InformationNotFoundException If no tweet associated with given tweet id
     */
    public Tweet getTweet(Long tweetId) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isPresent()) {
            return tweet.get();
        } else {
            throw new InformationNotFoundException("Tweet with the id " + tweetId + " is not found");
        }
    }
}
