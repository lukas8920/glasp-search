package org.kehrbusch;

import org.kehrbusch.failures.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final Publisher publisher;

    public JwtTokenFilterConfigurer(JwtTokenProvider jwtTokenProvider, LoginAttemptService loginAttemptService, Publisher publisher){
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginAttemptService = loginAttemptService;
        this.publisher = publisher;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        JwtTokenFilter filter = new JwtTokenFilter(jwtTokenProvider, loginAttemptService, publisher);
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
