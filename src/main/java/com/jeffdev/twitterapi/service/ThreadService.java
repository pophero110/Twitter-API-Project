package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.exception.InformationNotFoundException;
import com.jeffdev.twitterapi.model.Thread;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.repository.ThreadRepository;
import com.jeffdev.twitterapi.repository.TweetRepository;
import com.jeffdev.twitterapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ThreadService {
    private ThreadRepository threadRepository;
    private TweetRepository tweetRepository;
    private Logger logger = Logger.getLogger(ThreadService.class.getName());

    public static User getCurrentLoggedInUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }

    @Autowired
    public void setThreadRepository(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    @Autowired
    public void setTweetRepository(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    /**
     * Create a new tweet and add it to a thread
     * If the content in the given Tweet object is blank, an InformationInvalidException will be thrown
     * If the tweet can not be found by the given tweet id, an InformationNotFoundException will be thrown
     * If the tweet does not belong to any thread and has child tweets, add the new tweet to the thread that contains child tweets
     * Else create a new thread and add the new tweet to it
     * @param tweetId    the id of the tweet that reply to
     * @param replyTweet the Tweet object that need to be created
     * @return Thread with tweets that belongs to the thread
     * @throws InformationInvalidException
     * @throws InformationNotFoundException
     */
    public Tweet createThreadTweet(Long tweetId, Tweet replyTweet) {
        if (replyTweet.getContent().isBlank()) {
            throw new InformationInvalidException("Content can not be empty or contains only space character");
        }

        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        if (tweet.isEmpty()) {
            throw new InformationNotFoundException("The tweet with id " + tweetId + " does not exist");
        }

        if (tweet.get().getThread() == null && tweetRepository.existsByParentId(tweetId)) {
            Optional<Tweet> childTweet = tweetRepository.findFirstByParentId(tweetId);
            replyTweet.setThread(childTweet.get().getThread());
        } else {
            Thread newThread = threadRepository.save(new Thread());
            replyTweet.setThread(newThread);
        }

        replyTweet.setParentId(tweetId);
        replyTweet.setUser(getCurrentLoggedInUser());
        return tweetRepository.save(replyTweet);
    }


    /**
     * Find a thread by the given thread id and return the tweets in the thread
     * If the thread can not be found by the thread id, an InformationNotFoundException will be thrown
     *
     * @param threadId the id of the thread
     * @return a list of tweets
     * @throws InformationNotFoundException
     */
    public List<Tweet> getTweetsByThreadId(Long threadId) {
        Optional<Thread> thread = threadRepository.findById(threadId);
        if (thread.isPresent()) {
            return thread.get().getTweets();
        } else {
            throw new InformationNotFoundException("Thread with id " + threadId + " is not found");
        }
    }
}
