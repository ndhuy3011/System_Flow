package com.siin.auth.service;

import com.siin.auth.dto.emails.SendEmailAuthInputDTO;

public interface KafkaProducerService {
    public void sendMessage(String topic, String message);

    public void sendEmailAuth(SendEmailAuthInputDTO input);
}
