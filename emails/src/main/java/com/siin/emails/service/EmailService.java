package com.siin.emails.service;

import java.util.concurrent.CompletableFuture;

import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;

public interface EmailService {
    public CompletableFuture<Void> sendHtmlEmail(String to, String subject, String templateName, Context context)
            throws MessagingException;
}
