package com.siin.auth.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.siin.auth.dto.emails.SendEmailAuthInputDTO;
import com.siin.auth.service.KafkaProducerService;

import jakarta.annotation.Resource;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplateString;
    @Resource
    private KafkaTemplate<String, SendEmailAuthInputDTO> kafkaTemplateSendEmailAuthInputDTO;

    public void sendMessage(String topic, String message) {
        kafkaTemplateString.send(topic, message);
    }

    /**
     * Sends an email with authentication information using Kafka.
     *
     * @param input The input DTO containing information for sending the
     *              authentication email.
     * @since 2023-12-01
     * @author Siin
     */
    public void sendEmailAuth(SendEmailAuthInputDTO input) {
        // Specify the Kafka topic for sending email authentication information
        var topic = "send-email";

        // Send the authentication email information using Kafka Template
        kafkaTemplateSendEmailAuthInputDTO.send(topic, input);
    }

}
