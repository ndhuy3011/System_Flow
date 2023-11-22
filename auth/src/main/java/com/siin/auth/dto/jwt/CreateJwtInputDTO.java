package com.siin.auth.dto.jwt;

import lombok.Builder;

@Builder
public record CreateJwtInputDTO(String username, String password) {

}
