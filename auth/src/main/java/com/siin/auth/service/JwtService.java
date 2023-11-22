package com.siin.auth.service;

import com.siin.auth.dto.jwt.CreateJwtInputDTO;
import com.siin.auth.dto.jwt.CreateJwtOutDTO;

public interface JwtService {
    CreateJwtOutDTO createJwt(CreateJwtInputDTO input);
}
