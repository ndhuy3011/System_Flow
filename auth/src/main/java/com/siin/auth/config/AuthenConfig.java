package com.siin.auth.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class AuthenConfig {


    

    /**
     * Configures and provides a BCryptPasswordEncoder bean for password encoding.
     *
     * @return A BCryptPasswordEncoder bean.
     * @since 2023-11-22
     * @author Siin
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides a JwtEncoder bean for encoding JWTs using RSA keys.
     *
     * @param jwkSource The JWKSource used to retrieve JSON Web Keys (JWKs) for
     *                  encoding JWTs.
     * @return A JwtEncoder bean configured to use RSA keys.
     * @since 2023-11-22
     * @author Siin
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        // Return a JwtEncoder bean using the NimbusJwtEncoder implementation
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Configures and provides a JwtDecoder bean for decoding JWTs using the
     * provided JWKSource.
     *
     * @param jwkSource The JWKSource used to retrieve JSON Web Keys (JWKs) for
     *                  decoding JWTs.
     * @return A JwtDecoder bean configured with the specified JWKSource.
     * @since 2023-11-22
     * @author Siin
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        // Use OAuth2AuthorizationServerConfiguration's jwtDecoder method to configure
        // and create a JwtDecoder bean
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Configures and provides a JWKSource bean for managing JSON Web Keys (JWKs).
     *
     * @return A JWKSource bean containing a generated RSA key pair.
     * @throws IllegalStateException If there is an exception during the RSA key
     *                               pair generation process.
     * @since 2023-11-22
     * @author Siin
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        // Generate an RSA key pair
        KeyPair keyPair = generateRsaKey();

        // Extract public and private keys from the key pair
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        // Build an RSAKey with a random key ID
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        // Create a JWKSet containing the generated RSA key
        JWKSet jwkSet = new JWKSet(rsaKey);

        // Return an ImmutableJWKSet as the JWKSource bean
        return new ImmutableJWKSet<>(jwkSet);
    }

    /**
     * Generates an RSA key pair with a specified key size.
     *
     * @return The generated RSA key pair.
     * @throws IllegalStateException If there is an exception during the key pair
     *                               generation process.
     * @since 2023-11-22
     * @author Siin
     */
    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            // Initialize the KeyPairGenerator with the RSA algorithm and a key size of 2048
            // bits
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            // Generate the RSA key pair
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception ex) {
            // Throw an IllegalStateException if there is an exception during the key pair
            // generation process
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

}
