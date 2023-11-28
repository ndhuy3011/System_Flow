package com.siin.auth.dto.emails;

import lombok.Builder;

@Builder
public record SendEmailAuthInputDTO(String email, String otp, String name) {

}
