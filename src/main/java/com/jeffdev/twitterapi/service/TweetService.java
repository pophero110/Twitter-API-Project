package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
