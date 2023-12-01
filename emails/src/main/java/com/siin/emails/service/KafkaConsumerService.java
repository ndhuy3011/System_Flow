package com.siin.emails.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.siin.emails.dto.auth.SendEmailAuthInputDTO;

public interface KafkaConsumerService {
    void listenSendEmailAuth(ConsumerRecord<String, SendEmailAuthInputDTO> record);
}
