package com.siin.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.siin.auth.models.User;

import jakarta.persistence.LockModeType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsernameLockOptional(@Param("username") String username);

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByUsernameAndUuid(String username, UUID uuid);

}
