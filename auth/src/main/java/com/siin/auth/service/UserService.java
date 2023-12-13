package com.siin.auth.service;

import java.util.UUID;

import com.siin.auth.dto.user.ChangePasswordInputDTO;
import com.siin.auth.dto.user.CreateResetEmailInputDTO;
import com.siin.auth.dto.user.CreateRestEmailOutDTO;
import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.dto.user.GetInfoUserOutDTO;
import com.siin.auth.models.User;

public interface UserService {
    User create(User user);

    User getInfoFindByUsername(String username, boolean isLock);

    User getInfoFindByUsernameAndUUid(String username, UUID uuid);

    CreateUserOutDTO createUser(CreateUserInputDTO input);

    GetInfoUserOutDTO getInfoUserAuth();

    CreateRestEmailOutDTO restEmailAuth(CreateResetEmailInputDTO input);

    GetInfoUserOutDTO changePassword(ChangePasswordInputDTO input);

}
