package com.siin.emails.service;

import com.siin.emails.dto.auth.SendEmailAuthInputDTO;

public interface AuthEmailService {
    void sendEmailAuth(SendEmailAuthInputDTO input);
}
