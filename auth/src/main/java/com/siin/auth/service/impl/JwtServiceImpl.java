package com.siin.auth.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.siin.auth.dto.jwt.CreateJwtInputDTO;
import com.siin.auth.dto.jwt.CreateJwtOutDTO;
import com.siin.auth.dto.otp.CreateOtpDTO;
import com.siin.auth.entity.User;
import com.siin.auth.service.JwtService;
import com.siin.auth.service.OtpService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Resource
    private JwtEncoder jwtEncoder;

    @Resource
    private OtpService otpService;

    /**
     * Creates a JSON Web Token (JWT) for the given user.
     *
     * @param user The user for whom the JWT is created.
     * @return The JWT as a String.
     * @since 2023-11-22
     * @author Siin
     */
    private String createJwt(User user) {

        // Create a JwtClaimsSet object
        JwtClaimsSet jwt = JwtClaimsSet.builder()

                // Set the issuer of the JWT
                .issuer("Auth-issuer")

                // Set the subject of the JWT (typically the user's UUID)
                .subject(user.getUuid().toString())

                // Set the audience of the JWT (typically the authorized parties)
                .audience(List.of("Auth-audience"))

                // Set the expiration time of the JWT (e.g., 30 minutes from now)
                .expiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                .build();

        // Encode the JWT using the configured JwtEncoder
        return jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();
    }

    /**
     * Creates a reference JSON Web Token (JWT) for the given user.
     *
     * @param user The user for whom the reference JWT is created.
     * @return The reference JWT as a String.
     * @since 2023-11-22
     * @author Siin
     */
    private String createRefJwt(User user) {

        // Create a JwtClaimsSet object for the reference JWT
        JwtClaimsSet jwt = JwtClaimsSet.builder()

                // Set the issuer of the reference JWT
                .issuer("Auth-issuer-ref")

                // Set the subject of the reference JWT (typically the user's UUID)
                .subject(user.getUuid().toString())

                // Set the audience of the reference JWT (typically the authorized parties)
                .audience(List.of("Auth-audience-ref"))

                // Set the expiration time of the reference JWT (e.g., 60 minutes from now)
                .expiresAt(Instant.now().plus(60, ChronoUnit.MINUTES))
                .build();

        // Encode the reference JWT using the configured JwtEncoder
        return jwtEncoder.encode(JwtEncoderParameters.from(jwt)).getTokenValue();
    }

    /**
     * Creates JWTs for the user based on the provided input and returns the
     * corresponding output DTO.
     *
     * @param input The input DTO containing user credentials.
     * @return The output DTO containing access and reference JWTs.
     * @since 2023-11-22
     * @author Siin
     */
    public CreateJwtOutDTO createJwt(CreateJwtInputDTO input) {

        log.info("JwtServiceImpl.createJwt");
        // Create an authentication object using the provided username and password
        Authentication authentication = new UsernamePasswordAuthenticationToken(input.username(), input.password());

        // Set the authentication context with the created authentication
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Retrieve the authenticated user from the authentication principal
        var user = (User) authentication.getPrincipal();

        //Create create the OTP and Send OTP from user
        otpService.createOtp(CreateOtpDTO.builder().username(user.getUsername()).uuid(user.getUuid()).build());

        // Create and return the output DTO containing access and reference JWTs
        return CreateJwtOutDTO.builder()
                .accessToken(createJwt(user))
                .refToken(createRefJwt(user))
                .build();
    }

}
