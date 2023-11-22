package com.siin.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siin.auth.dto.jwt.CreateJwtInputDTO;
import com.siin.auth.dto.jwt.CreateJwtOutDTO;
import com.siin.auth.service.JwtService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<CreateJwtOutDTO> createUser(@RequestBody CreateJwtInputDTO input) {
        return ResponseEntity.ok(jwtService.createJwt(input));
    }
}
