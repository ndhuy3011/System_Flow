package com.siin.emails.service.impl;

import java.util.concurrent.CompletableFuture;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.siin.emails.service.EmailService;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * Asynchronously sends an HTML email using the provided parameters.
     *
     * @param to           The recipient's email address.
     * @param subject      The subject of the email.
     * @param templateName The name of the HTML email template.
     * @param context      The Thymeleaf context containing template variables.
     * @return A CompletableFuture representing the asynchronous task.
     * @throws RuntimeException If there is a failure in sending the email.
     * @since 2023-11-27
     * @author Siin
     */
    @Async
    @Transactional
    public CompletableFuture<Void> sendHtmlEmail(String to, String subject, String templateName, Context context) {
        try {
            // Create a MimeMessage for sending HTML emails
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            // Create a MimeMessageHelper to assist in setting up the MimeMessage
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);

            // Process the Thymeleaf template with the provided context to generate HTML
            // content
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            // Send the MimeMessage using the configured JavaMailSender
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Log the exception and throw a RuntimeException if there is a failure in
            // sending the email
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }

        // Return a CompletableFuture representing the completion of the asynchronous
        // task
        return CompletableFuture.completedFuture(null);
    }

}
