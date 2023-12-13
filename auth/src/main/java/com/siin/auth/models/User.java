package com.siin.auth.models;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.siin.auth.enums.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@DynamicUpdate
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userNo;

    @Column
    private String name;

    @Column(unique = true, updatable = false)
    @Email
    private String username;
    @Column
    private UUID uuid;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private ERole role;

    @Column
    private boolean isAccountNonExpired;

    @Column
    private boolean isAccountNonLocked;

    @Column
    private boolean isCredentialsNonExpired;

    @Column
    private boolean isEnabled;

    /**
     * An embeddable class representing the key for a user.
     */

    /**
     * Retrieves the authorities for the user based on their role.
     *
     * @return A collection of GrantedAuthority objects representing the user's
     *         role.
     * @since 2023-11-22
     * @author Siin
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

}
