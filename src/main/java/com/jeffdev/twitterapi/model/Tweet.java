package com.jeffdev.twitterapi.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

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
    private String content;


    // Source: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing.annotations
    // Source: https://www.logicbig.com/tutorials/spring-framework/spring-data/basic-auditing.html
    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private Instant updatedAt;

    public Tweet() {
    }

    public Tweet(long id, String content, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
