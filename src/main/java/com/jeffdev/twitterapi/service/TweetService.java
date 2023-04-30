package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationExistException;
import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.exception.InformationNotFoundException;
import com.jeffdev.twitterapi.model.Hashtag;
import com.jeffdev.twitterapi.model.Tweet;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.repository.HashtagRepository;
import com.jeffdev.twitterapi.repository.TweetRepository;
import com.jeffdev.twitterapi.repository.UserRepository;
import com.jeffdev.twitterapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;

    private final UserRepository userRepository;

    private final HashtagRepository hashtagRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, UserRepository userRepository, HashtagRepository hashtagRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.hashtagRepository = hashtagRepository;
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

    /**
     * Return a list of tweets that belongs the user of given user id
     *
     * @param userId the given user id
     * @return a list of tweet sorted by created_at desc
     * @throws InformationNotFoundException If the user of given user id is not found
     */
    public List<Tweet> getTweetsByUser(Long userId) {
        if (userRepository.existsById(userId)) {
            return tweetRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
        } else {
            throw new InformationNotFoundException("User with id " + userId + " is not found");
        }

    }

    /**
     * Return current user's tweets
     *
     * @return a list of tweet sorted by created_at desc
     */
    public List<Tweet> getMyTweets() {
        return tweetRepository.findAllByUserIdOrderByCreatedAtDesc(getCurrentLoggedInUser().getId());
    }


    /**
     * Adds a hashtag to the tweet with the given ID.
     * If the addedHashtag does not exist, save it to database
     * @param tweetId      the ID of the tweet to which the hashtag will be added
     * @param addedHashtag the hashtag to be added to the tweet
     * @return the updated tweet object
     * @throws InformationNotFoundException if the current user does not have a tweet with the given ID
     * @throws InformationExistException    if the tweet already has the given hashtag
     * @throws InformationInvalidException if the name of addedHashtag is blank
     */
    public Tweet addHashtag(Long tweetId, Hashtag addedHashtag) {
        Tweet tweet = tweetRepository.findByIdAndUserId(tweetId, getCurrentLoggedInUser().getId())
                .orElseThrow(() -> new InformationNotFoundException("You don't have any tweet with id " + tweetId));
        if (addedHashtag.getName().isBlank() || !addedHashtag.getName().startsWith("#"))
            throw new InformationInvalidException("Hashtag can not be empty or only contains space character");
        Optional<Hashtag> existingHashtag = hashtagRepository.findHashtagByName(addedHashtag.getName());
        if (existingHashtag.isPresent()) {
            if (tweet.getHashtags().contains(existingHashtag.get())) {
                throw new InformationExistException("Tweet with the id " + tweetId + " already has the hashtag " + addedHashtag.getName());
            }
            tweet.getHashtags().add(existingHashtag.get());
        } else {
            tweet.getHashtags().add(addedHashtag);
        }
        return tweetRepository.save(tweet);
    }

    /**
     * Return a list of tweets that related to the given list of hashtags
     * @param hashtags A list of hashtags
     * @return a list of tweets or an empty list
     */
    public List<Tweet> searchTweetByHashtags(List<String> hashtags) {
        List<Tweet> tweets = new ArrayList<>();
        hashtags.forEach(hashtagName -> {
            Optional<Hashtag> hashtag = hashtagRepository.findHashtagByName(hashtagName);
            hashtag.ifPresent(value -> tweets.addAll(tweetRepository.findByHashtagsId(value.getId())));
        });
        // sort the tweets by created_at in descending order
        tweets.sort((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()));
        return tweets;
    }
}
