package com.siin.auth.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_otp")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Otp {

    @EmbeddedId
    private OtpKey userKey;

    @Column
    private String otpNo;

    @Embeddable
    @Getter
    @Setter
    public static class OtpKey {
        private UUID uuidUser;
        private String username;

        /**
         * @param uuidUser
         * @param username
         */
        public OtpKey(UUID uuidUser, String username) {
            this.uuidUser = uuidUser;
            this.username = username;
        }

    }

}
