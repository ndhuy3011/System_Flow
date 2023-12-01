package com.siin.emails.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.siin.emails.dto.auth.SendEmailAuthInputDTO;
import com.siin.emails.service.AuthEmailService;

import jakarta.annotation.Resource;

@Service

public class KafkaConsumerServiceImpl {

    @Resource
    AuthEmailService authEmailService;

    /**
     * Kafka listener method that listens to the "send-email" topic and processes
     * incoming authentication email messages.
     *
     * @param input The Kafka ConsumerRecord containing information for sending the
     *              authentication email.
     * @since 2023-12-01
     * @author Siin
     */
    @KafkaListener(topics = "send-email")
    public void listenSendEmailAuth(ConsumerRecord<String, SendEmailAuthInputDTO> input) {
        // Delegate the processing of authentication email to the AuthEmailService
        authEmailService.sendEmailAuth(input.value());
    }

}
