package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

    List<Tweet> findAllByOrderByCreatedAtDesc();

    Optional<Tweet> findByIdAndUserId(Long tweetId, Long userId);

    // find child tweets by parent id
    Optional<Tweet> findFirstByParentId(Long parentId);

    // check whether a tweet has any child tweets
    boolean existsByParentId(Long parentId);

    // find all tweets posted by a user
    List<Tweet> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
