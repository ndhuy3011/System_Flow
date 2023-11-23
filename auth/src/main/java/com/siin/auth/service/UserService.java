package com.siin.auth.service;

import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.dto.user.GetInfoUserOutDTO;
import com.siin.auth.entity.User;

public interface UserService {
    User create(User user);

    User getInfoFindByUsername(String username);

    CreateUserOutDTO createUser(CreateUserInputDTO input);

    GetInfoUserOutDTO getInfoUserAuth();

}
