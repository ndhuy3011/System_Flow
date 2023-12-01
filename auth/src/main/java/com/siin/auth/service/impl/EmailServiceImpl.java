package com.siin.auth.service.impl;

import org.springframework.stereotype.Service;

import com.siin.auth.clients.EmailFeignClient;
import com.siin.auth.dto.emails.SendEmailAuthInputDTO;
import com.siin.auth.service.EmailService;
import com.siin.auth.service.KafkaProducerService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    KafkaProducerService kafkaProducer;

    @Resource
    EmailFeignClient emailFeignClient;

    /**
     * Sends an authentication email with the provided OTP to the specified user's
     * email address.
     *
     * @param username The email address of the user.
     * @param otp      The One-Time Password (OTP) to be included in the email.
     * @param name     The name of the user.
     * @since 2023-11-28
     * @author Siin
     */
    public void sendEmailAuthClient(String username, String otp, String name) {
        log.info("EmailServiceImpl.sendEmailAuthClient");
        // Send an authentication email using Feign Client
        emailFeignClient.sendEmailAuthI(SendEmailAuthInputDTO.builder()
                .email(username)
                .otp(otp)
                .name(name)
                .build());
    }

    /**
     * Sends an authentication email with the provided OTP to the specified user's
     * email address using Kafka.
     *
     * @param username The email address of the user.
     * @param otp      The One-Time Password (OTP) to be included in the email.
     * @param name     The name of the user.
     * @since 2023-12-01
     * @author Siin
     */
    public void sendEmailAuthKafka(String username, String otp, String name) {
        log.info("EmailServiceImpl.sendEmailAuthKafka");
        // Send an authentication email using Kafka Producer
        kafkaProducer.sendEmailAuth(SendEmailAuthInputDTO.builder()
                .email(username)
                .otp(otp)
                .name(name)
                .build());
    }

}
