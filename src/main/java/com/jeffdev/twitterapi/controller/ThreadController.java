package com.jeffdev.twitterapi.controller;


import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/threads")
public class ThreadController {

    private ThreadService threadService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    /**
     * Create a new tweet that is used to reply to other tweet
     * @param tweetId the id of the tweet that reply to
     * @param tweetObject the tweet used for replying
     * @return created tweet
     */
    @PostMapping("/tweets/{tweetId}")
    public Tweet reply(@PathVariable Long tweetId, @Valid @RequestBody Tweet tweetObject) {
        return threadService.createThreadTweet(tweetId, tweetObject);
    }

    /**
     * Get a list of tweets that belongs to a thread
     * @param threadId the id the threads
     * @return a list of tweets
     */
    @GetMapping("/{threadId}")
    public List<Tweet> getTweets(@PathVariable Long threadId) {
        return threadService.getTweetsByThreadId(threadId);
    }
}
