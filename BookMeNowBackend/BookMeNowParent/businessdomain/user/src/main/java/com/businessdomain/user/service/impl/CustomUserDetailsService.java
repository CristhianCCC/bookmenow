package com.businessdomain.user.service.impl;

import com.businessdomain.user.common.CustomUserDetails;
import com.businessdomain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * Used by the security framework to load user-specific data during authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Loads the user details by email (used as username in this system).
     *
     * @param email the email of the user
     * @return a {@link UserDetails} object containing user data for authentication
     * @throws UsernameNotFoundException if the user is not found in the repository
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
    }
}
