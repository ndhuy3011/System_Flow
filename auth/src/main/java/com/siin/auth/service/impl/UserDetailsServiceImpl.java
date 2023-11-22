package com.siin.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.siin.auth.repository.UserRepository;

import jakarta.annotation.Resource;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserRepository userRepository;

    /**
     * Loads a user by their username for authentication purposes.
     *
     * @param username The username of the user to load.
     * @return A UserDetails object representing the loaded user.
     * @throws UsernameNotFoundException If the user is not found.
     * @since 2023-11-22
     * @author Siin
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            // Attempt to retrieve the user from the repository by username
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        } catch (Exception ex) {
            // If an exception occurs during user retrieval, log an error and throw a
            throw new UsernameNotFoundException("Error retrieving user by username: " + username, ex);
        }
    }

}
