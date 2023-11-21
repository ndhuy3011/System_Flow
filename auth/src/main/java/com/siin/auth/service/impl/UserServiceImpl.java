package com.siin.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.entity.User;
import com.siin.auth.repository.UserRepository;
import com.siin.auth.service.UserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a new user and saves it to the repository.
     * 
     * @param user The user object to be created and saved.
     * @return The created user object.
     * 
     * @throws IllegalArgumentException If the user is null, or if the user's key is
     *                                  not null.
     * @throws IllegalArgumentException If the username, password, or name fields of
     *                                  the user are null.
     * @throws IllegalArgumentException If the provided username already exists in
     *                                  the repository.
     */
    public User create(User user) {
        log.info("UserServiceImpl.create begin");

        // Ensure that the user object is not null
        Assert.notNull(user, "User is not null");

        // Ensure that the user's key is null (assuming that null is the expected value
        // for a new user)
        Assert.isNull(user.getUserKey(), "Key is null");

        // Ensure that the username, password, and name fields are not null
        Assert.notNull(user.getUsername(), "Username is not null");
        Assert.notNull(user.getPassword(), "Password is not null");
        Assert.notNull(user.getName(), "Name is not null");

        // Check if the username already exists in the repository
        Assert.isTrue(!userRepository.existsByUsername(user.getUsername()), "Username is exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the repository
        userRepository.save(user);

        log.info("UserServiceImpl.create end");
        // Return an output DTO with the key of the created user
        return user;
    }

    /**
     * Creates a new user based on the provided input and returns the corresponding
     * output DTO.
     *
     * @param input The input DTO containing user information.
     * @return The output DTO containing the key of the created user.
     */
    public CreateUserOutDTO createUser(CreateUserInputDTO input) {
        log.info("UserServiceImpl.createUser begin");

        // Build a new user using the input DTO, encoding the password
        var user = create(User.builder()
                .name(input.name())
                .username(input.username())
                .password(input.password())
                .build());

        log.info("UserServiceImpl.createUser end");

        // Return an output DTO with the key of the created user
        return CreateUserOutDTO.builder().key(user.getUserKey()).build();
    }

}
