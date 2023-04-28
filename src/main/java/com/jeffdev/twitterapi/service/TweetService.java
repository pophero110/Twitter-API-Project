package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Tweet tweet = new Tweet();
        try {
            if (tweetObject.getContent().isBlank()) {
                tweet.setContent("Content can not be empty or contains only space character");
            }
            else if (tweetObject.getContent().length() > 280) {
                tweet.setContent("Content can not be longer than 280 character");
            } else {
                return tweetRepository.save(tweetObject);
            }
            return tweet;
        } catch (Exception e) {
            tweet.setContent(e.getMessage());
            return tweet;
        }
    }
}
