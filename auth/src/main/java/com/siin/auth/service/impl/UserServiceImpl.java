package com.siin.auth.service.impl;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.siin.auth.dto.otp.CreateOtpDTO;
import com.siin.auth.dto.user.ChangePasswordInputDTO;
import com.siin.auth.dto.user.CreateResetEmailInputDTO;
import com.siin.auth.dto.user.CreateRestEmailOutDTO;
import com.siin.auth.dto.user.CreateUserInputDTO;
import com.siin.auth.dto.user.CreateUserOutDTO;
import com.siin.auth.dto.user.GetInfoUserOutDTO;
import com.siin.auth.message.user.ExceptionMessageUser;
import com.siin.auth.models.User;
import com.siin.auth.repository.UserRepository;
import com.siin.auth.service.EmailService;
import com.siin.auth.service.OtpService;
import com.siin.auth.service.UserService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

        @Resource
        private UserRepository userRepository;

        @Resource
        private PasswordEncoder passwordEncoder;

        @Resource
        private OtpService otpService;

        @Resource
        private EmailService emailService;

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
         * @author Siin
         * @since 2023-11-21
         */
        @Transactional
        public User create(User user) {
                log.info("UserServiceImpl.create begin");

                // Ensure that the user object is not null
                Assert.notNull(user, ExceptionMessageUser.USER_NOT_NULL);

                // Ensure that the user's key is null (assuming that null is the expected value
                // for a new user)

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
         * @author Siin
         * @since 2023-11-21
         */
        @Transactional
        public CreateUserOutDTO createUser(CreateUserInputDTO input) {
                log.info("UserServiceImpl.createUser begin");

                // Build a new user using the input DTO, encoding the password
                var user = create(User.builder()
                                .name(input.name())
                                .username(input.username())
                                .password(input.password())
                                .build());
                // Build a new otp using the input DTO
                var otp = otpService
                                .createOtp(CreateOtpDTO.builder().username(user.getUsername()).uuid(user.getUuid())
                                                .build());
                // Send Email Auth
                emailService.sendEmailAuthKafka(user.getUsername(), otp.getOtpNo(), user.getName());

                log.info("UserServiceImpl.createUser end");

                // Return an output DTO with the key of the created user
                return CreateUserOutDTO.builder().uuid(user.getUuid()).build();
        }

        /**
         * Retrieves user information based on the authenticated user and returns the
         * corresponding output DTO.
         *
         * @return The output DTO containing user information.
         * @since 2023-11-23
         * @author Siin
         */
        public GetInfoUserOutDTO getInfoUserAuth() {
                log.info("UserServiceImpl.getInfoUserAuth");
                // Retrieve the authenticated user from the security context
                var userAuth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                // Retrieve detailed user information based on the authenticated user's username
                var user = getInfoFindByUsername(userAuth.getUsername(), false);

                // Build and return the output DTO containing user information
                return GetInfoUserOutDTO.builder()
                                .name(user.getName())
                                .build();
        }

        /**
         * Retrieves detailed user information based on the provided username.
         *
         * @param username The username of the user to retrieve information for.
         * @return The User object containing detailed information.
         * @throws NoSuchElementException If the user is not found.
         * @since 2023-11-23
         * @author Siin
         */
        @Transactional
        public User getInfoFindByUsername(String username, boolean isLock) {
                log.info("UserServiceImpl.getInfoFindByUsername");
                // Ensure that the username fields are not null
                Assert.notNull(username, ExceptionMessageUser.USERNAME_NOT_NULL);

                if (isLock) {
                        return userRepository.findByUsernameLockOptional(username)
                                        .orElseThrow(() -> new NoSuchElementException(
                                                        ExceptionMessageUser.NOT_FOUND_USERNAME + username));
                }

                // Attempt to retrieve the user from the repository by username
                return userRepository.findByUsername(username)
                                .orElseThrow(() -> new NoSuchElementException(
                                                ExceptionMessageUser.NOT_FOUND_USERNAME + username));
        }

        /**
         * Retrieves user information based on the provided username and UUID.
         *
         * @param username The username of the user.
         * @param uuid     The UUID of the user.
         * @return The user entity.
         * @throws NoSuchElementException If the user is not found.
         * @since 2023-11-28
         * @author Siin
         */
        public User getInfoFindByUsernameAndUUid(String username, UUID uuid) {
                // Log information about the method invocation
                log.info("UserServiceImpl.getInfoFindByUsernameAndUUid");

                // Retrieve the user based on the provided username and UUID
                return userRepository.findByUsernameAndUuid(username, uuid)
                                .orElseThrow(() -> new NoSuchElementException(
                                                ExceptionMessageUser.NOT_FOUND_USERNAME + username));
        }

        /**
         * Initiates the process for resetting the email authentication by generating a
         * new OTP,
         * sending it to the user's email address, and updating the OTP in the system.
         *
         * @param input The input DTO containing information for resetting the email
         *              authentication.
         * @return The output DTO indicating the status of the email authentication
         *         reset.
         * @throws NoSuchElementException If the user is not found.
         * @since 2023-11-28
         * @author Siin
         */
        public CreateRestEmailOutDTO restEmailAuth(CreateResetEmailInputDTO input) {
                // Retrieve the user based on the provided UUID
                var user = userRepository.findByUuid(input.uuid())
                                .orElseThrow(() -> new NoSuchElementException(
                                                ExceptionMessageUser.NOT_FOUND_UUID + input.uuid()));

                // Generate a new OTP for the user's email authentication and update the OTP in
                // the system
                var otp = otpService
                                .refOtp(CreateOtpDTO.builder().username(user.getUsername()).uuid(input.uuid()).build());

                // Send the authentication email with the new OTP to the user
                emailService.sendEmailAuthKafka(user.getUsername(), otp.getOtpNo(), user.getName());

                // Return an output DTO indicating the success of the email authentication reset
                return CreateRestEmailOutDTO.builder().message("OK").build();
        }

        /**
         * Changes the password for the user specified in the input DTO.
         *
         * @param input The input DTO containing information for changing the password.
         * @return The output DTO containing user information after the password change.
         * @throws BusinessException If the old password is incorrect or the new
         *                           password is not different.
         * @since 2023-12-01
         * @author Siin
         */
        @Transactional
        public GetInfoUserOutDTO changePassword(ChangePasswordInputDTO input) {
                // Retrieve user information with additional details using the specified
                // username
                var user = getInfoFindByUsername(input.username(), true);

                // Ensure that the provided old password matches the current user password
                Assert.isTrue(passwordEncoder.matches(input.oldPassword(), user.getPassword()),
                                ExceptionMessageUser.PASSWORD_NOT_CORRET);

                // Ensure that the new password is different from the current user password
                Assert.isTrue(passwordEncoder.matches(input.newPassword(), user.getPassword()),
                                ExceptionMessageUser.PASSWORD_NOT_CHANGER);

                // Update the user password with the encoded new password
                user.setPassword(passwordEncoder.encode(input.newPassword()));
                userRepository.save(user);

                // Build and return the output DTO containing user information after the
                // password change
                return GetInfoUserOutDTO.builder()
                                .name(user.getName())
                                .build();
        }

}
