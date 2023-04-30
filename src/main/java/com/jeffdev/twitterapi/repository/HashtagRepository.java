package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findHashtagByName(String name);
}
