package com.jeffdev.twitterapi.controller;


import com.jeffdev.twitterapi.model.Hashtag;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Tweet createTweet(@Valid @RequestBody Tweet tweetObject) {
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
    public Tweet updateTweet(@PathVariable Long tweetId, @Valid @RequestBody Tweet tweetObject) {
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


    /**
     * return a tweet by given tweet id
     *
     * @param tweetId the given tweet id
     * @return tweet
     */
    @GetMapping("/{tweetId}")
    public Tweet getTweet(@PathVariable Long tweetId) {
        return tweetService.getTweet(tweetId);
    }

    /**
     * return a list of tweets that belongs to the user of given user id
     *
     * @param userId the given user id
     * @return a list of tweets
     */
    @GetMapping("/users/{userId}")
    public List<Tweet> getTweetsByUser(@PathVariable Long userId) {
        return tweetService.getTweetsByUser(userId);
    }

    /**
     * return current user's tweets
     *
     * @return a list of tweets
     */
    @GetMapping("/me")
    public List<Tweet> getMyTweets() {
        return tweetService.getMyTweets();
    }


    /**
     * Adds a hashtag to the specified tweet.
     * @param tweetId the ID of the tweet to add the hashtag to
     * @param hashtag the hashtag to add
     * @return the updated tweet with the added hashtag
     */
    @PostMapping("/{tweetId}/hashtags")
    public Tweet addHashTag(@PathVariable Long tweetId, @RequestBody Hashtag hashtag) {
        return tweetService.addHashtag(tweetId, hashtag);
    }


    /**
     * Take a hashtags query param and return a list of tweets that related to the hashtags
     * @param hashtags the hashtag query param
     * @return a list of tweets that related to the hashtags
     */
    @GetMapping("/search")
    public List<Tweet> searchTweetByHashtags(@RequestParam List<String> hashtags ) {
        return tweetService.searchTweetByHashtags(hashtags);
    }
}
