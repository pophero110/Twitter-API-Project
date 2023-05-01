package com.jeffdev.twitterapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tweets")
@EntityListeners(AuditingEntityListener.class)
public class Tweet {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Source: https://www.objectdb.com/api/java/jpa/Column
    @Column(nullable = false, length = 280)
    @NotBlank
    private String content;


    // Source: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing.annotations
    // Source: https://www.logicbig.com/tutorials/spring-framework/spring-data/basic-auditing.html
    @CreatedDate
    @JsonProperty("created_at")
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Column
    @JsonProperty("parent_id")
    private long parentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

    @ManyToMany(cascade = {
            CascadeType.ALL
    })
    @JoinTable(
            name = "tweet_hashtag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
    Set<Hashtag> hashtags = new HashSet<>();

    public Tweet() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Set<Hashtag> getHashtags() {
        return hashtags;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", user=" + user +
                ", thread=" + thread +
                '}';
    }
}
