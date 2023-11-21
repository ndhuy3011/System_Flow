package com.siin.auth.dto.user;

import com.siin.auth.entity.User.UserKey;

import lombok.Builder;
@Builder
public record CreateUserOutDTO(UserKey key) {
}