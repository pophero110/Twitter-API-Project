package com.jeffdev.twitterapi.controller;


import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityListeners;
import java.util.List;

@RestController
@RequestMapping(path = "/api/threads")
public class ThreadController {

    private ThreadService threadService;

    @Autowired
    public void setThreadService(ThreadService threadService) {
        this.threadService = threadService;
    }

    @PostMapping("/tweets/{tweetId}")
    public Tweet reply(@PathVariable Long tweetId, @RequestBody Tweet tweetObject) {
        return threadService.createThreadTweet(tweetId, tweetObject);
    }

    @GetMapping("/{threadId}")
    public List<Tweet> getTweets(@PathVariable Long threadId) {
        return threadService.getTweetsByThreadId(threadId);
    }
}
