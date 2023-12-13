package com.siin.auth.dto.user;

import lombok.Builder;

@Builder
public record ChangePasswordInputDTO(String username, String oldPassword, String newPassword) {

}
