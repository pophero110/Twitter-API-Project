package com.jeffdev.twitterapi.repository;

import com.jeffdev.twitterapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmailAddress(String emailAddress);

    boolean existsById(Long userId);
}
