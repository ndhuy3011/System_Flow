package com.siin.emails.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siin.emails.dto.auth.SendEmailAuthInputDTO;
import com.siin.emails.service.AuthEmailService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/auth")
public class EmailAuthController {

    @Resource
    private AuthEmailService authEmailService;

    @PostMapping("/otp")
    public ResponseEntity<String> postSendEmailAuth(@RequestBody SendEmailAuthInputDTO input) {
        authEmailService.sendEmailAuth(input);
        return ResponseEntity.ok("OK");
    }

}
