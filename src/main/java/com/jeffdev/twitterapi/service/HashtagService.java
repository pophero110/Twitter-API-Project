package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.model.Hashtag;
import com.jeffdev.twitterapi.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashtagService {
    private HashtagRepository hashtagRepository;

    @Autowired
    public void setHashtagRepository(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    public List<Hashtag> getTrendingHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        hashtags.sort((t1, t2) -> t2.getTweets().size() - (t1.getTweets().size()));
        return hashtags;
    }
}
