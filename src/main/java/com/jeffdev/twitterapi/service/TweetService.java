package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.exception.InformationNotFoundException;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Create a tweet in database and return it
     * @param tweetObject The tweet object that need to saved in database
     * @return The newly saved tweet
     */
    public Tweet createTweet(Tweet tweetObject) {
        try {
            if (tweetObject.getContent().isBlank()) {
                throw new InformationInvalidException("Content can not be empty or contains only space character");
            }
            else if (tweetObject.getContent().length() > 280) {
                throw new InformationInvalidException("Content can not be longer than 280 character");
            } else {
                return tweetRepository.save(tweetObject);
            }
        } catch (Exception e) {
            throw new InformationInvalidException(e.getMessage());
        }
    }

    /**
     * Get a list of tweets sorted by created_at desc
     * @return a list of tweets or an empty list
     */
    public List<Tweet> getTweets() {
        return tweetRepository.findAllByOrderByCreatedAtDesc();
    }


    /**
     * update a specific tweet by tweet id based on the tweet object
     * @param tweetId the id of the tweet that needs to be updated based on the tweet object
     * @return updated tweet
     * @throws InformationNotFoundException
     */
    public Tweet updateTweet(Long tweetId, Tweet tweetObject) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isPresent()) {
            if (tweetObject.getContent().isBlank()) {
                throw new InformationInvalidException("Content can not be empty or contains only space character");
            }
            else if (tweetObject.getContent().length() > 280) {
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
     * @param tweetId the id of the tweet
     * @return deleted tweet
     * @throws InformationNotFoundException
     */
    public Tweet deleteTweet(Long tweetId) {
        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isPresent()) {
            tweetRepository.delete(tweet.get());
            return tweet.get();
        } else {
            throw new InformationNotFoundException("Tweet is not found with id " + tweetId);
        }
    }
}
