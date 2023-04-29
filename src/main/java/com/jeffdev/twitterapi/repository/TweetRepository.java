package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByOrderByCreatedAtDesc();

    Optional<Tweet> findByIdAndAndUserId(Long tweetId, Long userId);
}
