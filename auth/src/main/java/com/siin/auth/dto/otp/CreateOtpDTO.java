package com.siin.auth.dto.otp;

import java.util.UUID;

import lombok.Builder;
@Builder
public record CreateOtpDTO(UUID uuid, String username) {

}
