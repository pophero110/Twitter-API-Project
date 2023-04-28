package com.jeffdev.twitterapi.controller;


import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tweets")
public class TweetController {

    private TweetService tweetService;

    @Autowired
    public void setTweetService(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    /**
     * Create a new tweet and return it
     *
     * @param tweetObject The tweet object to be created
     * @return The newly created tweet object
     */
    @PostMapping(path = "")
    public Tweet createTweet(@RequestBody Tweet tweetObject) {
        return tweetService.createTweet(tweetObject);
    }

    @GetMapping(path = "")
    public List<Tweet> getTweets() {
        return tweetService.getTweets();
    }

    /**
     * update a specific tweet based on tweet id and return updated tweet
     *
     * @param tweetId     the id of the tweet
     * @param tweetObject the object that contains attributes that is needed to be updated
     * @return updated tweet
     */
    @PutMapping(path = "/{tweetId}")
    public Tweet updateTweet(@PathVariable Long tweetId, @RequestBody Tweet tweetObject) {
        return tweetService.updateTweet(tweetId, tweetObject);
    }

    /**
     * delete a specific tweet based on tweet id and return deleted tweet
     *
     * @param tweetId the id of the tweet
     * @return deleted tweet
     */
    @DeleteMapping(path = "/{tweetId}")
    public Tweet deleteTweet(@PathVariable Long tweetId) {
        return tweetService.deleteTweet(tweetId);
    }
}
