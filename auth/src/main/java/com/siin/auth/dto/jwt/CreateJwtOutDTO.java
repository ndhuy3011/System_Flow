package com.siin.auth.dto.jwt;

import lombok.Builder;

@Builder
public record CreateJwtOutDTO(String accessToken, String refToken) {
    
}
