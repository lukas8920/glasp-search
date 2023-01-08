package org.kehrbusch;

import org.kehrbusch.entities.Jwt;
import org.kehrbusch.repositories.JwtRepository;
import org.kehrbusch.entities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class UserDetailsResponse {
    private static final Logger logger = Logger.getLogger(UserDetailsResponse.class.getName());

    private final SecurityConfig securityConfig;
    private final PasswordEncoder passwordEncoder;
    private final JwtRepository jwtRepository;

    @Autowired
    public UserDetailsResponse(SecurityConfig securityConfig, PasswordEncoder passwordEncoder, JwtRepository jwtRepository){
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;
        this.jwtRepository = jwtRepository;
    }

    public UserDetails loadUserByUsername(String profileId) throws UsernameNotFoundException {
        long id;
        try {
            id = Long.parseLong(profileId);
        } catch (NumberFormatException e){
            throw new UsernameNotFoundException("No user found - wrong id format provided");
        }

        final Jwt jwt = jwtRepository.findJwtById(id);
        if (jwt == null)
            throw  new UsernameNotFoundException("User with id " + id + " not found");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        jwt.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.toString())));

        return org.springframework.security.core.userdetails.User
                .withUsername(profileId)
                .password(passwordEncoder.encode(securityConfig.getPassword()))
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
