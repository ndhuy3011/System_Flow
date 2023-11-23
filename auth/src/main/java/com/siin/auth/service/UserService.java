package com.siin.auth.service;

import java.util.UUID;

import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.dto.user.GetInfoUserOutDTO;
import com.siin.auth.entity.User;

public interface UserService {
    User create(User user);

    User getInfoFindByUsername(String username);

    User getInfoFindByUsernameAndUUid(String username, UUID uuid);

    CreateUserOutDTO createUser(CreateUserInputDTO input);

    GetInfoUserOutDTO getInfoUserAuth();

}
