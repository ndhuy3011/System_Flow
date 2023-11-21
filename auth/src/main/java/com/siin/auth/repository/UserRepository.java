package com.siin.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.auth.entity.User;
import com.siin.auth.entity.User.UserKey;

@Repository
public interface UserRepository extends JpaRepository<User, UserKey> {
    boolean existsByUsername(String username);

}
