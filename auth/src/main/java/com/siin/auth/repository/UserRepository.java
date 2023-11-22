package com.siin.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.auth.entity.User;
import com.siin.auth.entity.User.UserKey;

@Repository
public interface UserRepository extends JpaRepository<User, UserKey> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
