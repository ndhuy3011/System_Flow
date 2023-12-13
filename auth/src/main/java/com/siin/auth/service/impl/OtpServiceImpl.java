package com.siin.auth.service.impl;

import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.siin.auth.dto.otp.CreateOtpDTO;
import com.siin.auth.models.Otp;
import com.siin.auth.repository.OtpRepository;
import com.siin.auth.service.OtpService;
import com.siin.auth.service.UserService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    @Resource
    private OtpRepository otpRepository;

    @Resource
    private UserService userService;

    /**
     * Creates and sends a One-Time Password (OTP) for the specified user.
     *
     * @param input The input DTO containing user information for OTP creation.
     * @return The generated OTP entity.
     * @throws NoSuchElementException If the user is not found.
     * @since 2023-11-23
     * @author Siin
     */
    @Transactional
    public Otp createOtp(CreateOtpDTO input) {
        log.info("OtpServiceImpl.createOtp");

        // Retrieve detailed user information based on the provided username
        var user = userService.getInfoFindByUsername(input.username(), false);

        // Create an OTP entity with a randomly generated six-digit number and the
        // user's key
        var otp = Otp.builder()
                .otpNo(generateRandomSixDigitNumber())
                .userKey(new Otp.OtpKey(user.getUuid(), user.getUsername()))
                .build();

        // Send the generated OTP

        // Save the OTP entity to the repository
        return otpRepository.save(otp);
    }

    /**
     * Generates a random six-digit number as a formatted string.
     *
     * @return A randomly generated six-digit number.
     * @since 2023-11-23
     * @author Siin
     */
    private static String generateRandomSixDigitNumber() {
        // Create a random number generator using SecureRandom
        Random random = new SecureRandom();

        // Generate a random integer between 0 (inclusive) and 1000000 (exclusive)
        int randomNumber = random.nextInt(1000000);

        // Format the random number as a six-digit string with leading zeros if
        // necessary
        return String.format("%06d", randomNumber);
    }

    /**
     * Generates and updates a new One-Time Password (OTP) for the specified user.
     *
     * @param input The input DTO containing user information for OTP regeneration.
     * @return The updated OTP entity.
     * @throws NoSuchElementException If the user or OTP is not found.
     * @since 2023-11-23
     * @author Siin
     */
    @Transactional
    public Otp refOtp(CreateOtpDTO input) {
        // Retrieve detailed user information based on the provided username
        var user = userService.getInfoFindByUsername(input.username(), false);

        // Retrieve the existing OTP entity based on the user's key
        var otp = otpRepository.findById(new Otp.OtpKey(user.getUuid(), user.getUsername()))
                .orElseThrow(() -> new NoSuchElementException("OTP not found for the user"));

        // Generate a new random six-digit number and update the OTP entity
        otp.setOtpNo(generateRandomSixDigitNumber());

        // Save the updated OTP entity to the repository
        return otpRepository.save(otp);
    }

}
