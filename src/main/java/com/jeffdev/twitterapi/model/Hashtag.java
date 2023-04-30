package com.jeffdev.twitterapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hashtags")
@EntityListeners(AuditingEntityListener.class)
public class Hashtag {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @CreatedDate
    @JsonProperty("created_at")
    private Instant createdAt;

    // https://www.bezkoder.com/jpa-many-to-many/
    @ManyToMany(mappedBy = "hashtags", cascade = {
            CascadeType.ALL
    })
    Set<Tweet> tweets = new HashSet<>();

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}
