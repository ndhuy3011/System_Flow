package com.siin.auth.dto.user;

import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateUserOutDTO(UUID uuid) {
}