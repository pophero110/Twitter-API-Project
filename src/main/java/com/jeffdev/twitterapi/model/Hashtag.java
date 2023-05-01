package com.jeffdev.twitterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min = 2, message = "Hashtag can not be empty")
    @Pattern(regexp = "^#.*", message = "Hashtag must start with '#'")
    private String name;

    @CreatedDate
    @JsonProperty("created_at")
    private Instant createdAt;

    // https://www.bezkoder.com/jpa-many-to-many/
    @ManyToMany(mappedBy = "hashtags", cascade = {
            CascadeType.ALL
    })
    @JsonIgnore
    Set<Tweet> tweets = new HashSet<>();

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Set<Tweet> getTweets() {
        return tweets;
    }
}
