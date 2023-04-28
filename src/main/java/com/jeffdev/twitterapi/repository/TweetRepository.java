package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
