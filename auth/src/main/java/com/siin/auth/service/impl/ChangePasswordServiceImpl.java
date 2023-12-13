package com.siin.auth.service.impl;

import org.springframework.stereotype.Service;

import com.siin.auth.models.ChangePassword;
import com.siin.auth.repository.ChangePasswordRepository;
import com.siin.auth.service.ChangePasswordService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Resource
    ChangePasswordRepository changePasswordRepository;

    @Override
    public ChangePassword createChangePassword(ChangePassword input) {
        log.info("ChangePasswordServiceImpl.createChangePassword");

        return changePasswordRepository.save(input);
    }

}
