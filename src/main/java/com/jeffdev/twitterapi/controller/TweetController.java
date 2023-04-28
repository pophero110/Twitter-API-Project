package com.jeffdev.twitterapi.controller;


import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param tweetObject The tweet object to be created
     * @return The newly created tweet object
     */
    @PostMapping(path = "")
    public Tweet createTweet(@RequestBody Tweet tweetObject) {
        return tweetService.createTweet(tweetObject);
    }
}
