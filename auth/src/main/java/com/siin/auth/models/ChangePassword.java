package com.siin.auth.models;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class ChangePassword {
    @Id
    private UUID uuid;

    @Column
    private String oldPassword;

    @Column
    private String newPassword;

    @Column
    private boolean isEnabled;

    @Column
    private Date expiresAt;

}
