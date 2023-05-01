package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.model.Hashtag;
import com.jeffdev.twitterapi.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class that handles hashtag-related operations.
 */
@Service
public class HashtagService {
    private HashtagRepository hashtagRepository;

    @Autowired
    public void setHashtagRepository(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    /**
     * Get a list of the trending hashtags, ordered by the number of tweets they appear in, in descending order.
     *
     * @return a list of the trending hashtags
     */
    public List<Hashtag> getTrendingHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        hashtags.sort((t1, t2) -> t2.getTweets().size() - (t1.getTweets().size()));
        return hashtags;
    }
}
