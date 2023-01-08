package org.kehrbusch;

import org.kehrbusch.util.CachingPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new CachingPasswordEncoder();
    }

    public String getPassword(){
        return ";6gXZ:f`=#62E9DdGDekQeh3%";
    }
}
