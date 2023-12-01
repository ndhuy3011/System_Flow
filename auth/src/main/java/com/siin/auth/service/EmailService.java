package com.siin.auth.service;

public interface EmailService {
    void sendEmailAuthClient(String username, String otp, String name);
    void sendEmailAuthKafka(String username, String otp, String name);
}
