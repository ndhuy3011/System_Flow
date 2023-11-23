package com.siin.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.auth.entity.Otp;
import com.siin.auth.entity.Otp.OtpKey;

@Repository
public interface OtpRepository extends JpaRepository<Otp, OtpKey> {

}
