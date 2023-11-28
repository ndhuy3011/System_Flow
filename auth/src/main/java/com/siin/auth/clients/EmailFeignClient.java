package com.siin.auth.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.siin.auth.dto.emails.SendEmailAuthInputDTO;

@FeignClient(name = "emails")
public interface EmailFeignClient {
    @GetMapping("/auth/otp")
    String sendEmailAuthI(SendEmailAuthInputDTO input);
}
