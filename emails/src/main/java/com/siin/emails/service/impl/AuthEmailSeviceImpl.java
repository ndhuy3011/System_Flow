package com.siin.emails.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.siin.emails.dto.auth.SendEmailAuthInputDTO;
import com.siin.emails.service.AuthEmailService;
import com.siin.emails.service.EmailService;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthEmailSeviceImpl implements AuthEmailService {
    @Resource
    protected EmailService emailService;

    /**
     * Sends an authentication email containing the provided OTP to the specified
     * email address.
     *
     * @param input The input DTO containing information for sending the
     *              authentication email.
     * @since 2023-11-28
     * @author Siin
     */
    public void sendEmailAuth(SendEmailAuthInputDTO input) {
        // Log information about the authentication email sending process
        log.info("AuthEmailSeviceImpl.sendEmailAuth");

        // Create a Thymeleaf context and set the OTP variable
        Context context = new Context();
        context.setVariable("otp", input.otp());

        try {
            // Send an HTML email with the OTP to the specified email address
            emailService.sendHtmlEmail(input.email(), "Otp", "SendEmailAuth", context);
        } catch (MessagingException e) {
            // Log the exception if there is an error in sending the email
            e.printStackTrace();
        }
    }

}
