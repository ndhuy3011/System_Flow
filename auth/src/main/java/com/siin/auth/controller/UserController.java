package com.siin.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.service.UserService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<CreateUserOutDTO> createUser(@RequestBody CreateUserInputDTO input) {
        return ResponseEntity.ok(userService.createUser(input));
    }


}
