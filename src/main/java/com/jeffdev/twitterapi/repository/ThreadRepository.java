package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
}
