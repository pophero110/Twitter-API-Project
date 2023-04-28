package com.jeffdev.twitterapi.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Source: https://www.objectdb.com/api/java/jpa/Column
    @Column(nullable = false, length = 280)
    private String content;


    // Source: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#auditing.annotations
    @CreatedDate
    private Instant createdAt;

    @LastModifiedBy
    private Instant updatedAt;
}
