package com.siin.auth.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.auth.models.ChangePassword;

@Repository
public interface ChangePasswordRepository extends JpaRepository<ChangePassword,UUID>{
    
}
