package com.siin.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.auth.models.Otp;
import com.siin.auth.models.Otp.OtpKey;

@Repository
public interface OtpRepository extends JpaRepository<Otp, OtpKey> {

}
