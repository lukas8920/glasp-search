package org.kehrbusch;

import org.kehrbusch.failures.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Value("${spring.profiles.active}")
    private String profile;

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginAttemptService loginAttemptService;
    private final Publisher publisher;

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider, LoginAttemptService loginAttemptService, Publisher publisher) {
        this.loginAttemptService = loginAttemptService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.publisher = publisher;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        if (profile.equals("dev")){
            httpSecurity.headers().frameOptions().disable();
            httpSecurity.authorizeHttpRequests()
                    .requestMatchers("/api-docs").permitAll()
                    .requestMatchers("/api-docs.yaml").permitAll()
                    .requestMatchers("/h2-console/**/**").permitAll()
                    .requestMatchers("/data/init/zipstreet").permitAll()
                    .requestMatchers("/data/init/city").permitAll()
                    .requestMatchers("/app/search/zipstreet").permitAll()
                    .requestMatchers("/app/search/zipstreetcity").permitAll();
        }

        if (profile.equals("prod")){
            httpSecurity.authorizeHttpRequests()
                    .requestMatchers("/api-docs").denyAll()
                    .requestMatchers("/api-docs.yaml").denyAll();
        }

        httpSecurity.authorizeHttpRequests()
                .requestMatchers("/data/login").permitAll()
                .requestMatchers("/auth/token/access").permitAll()
                .anyRequest().authenticated();

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, loginAttemptService, publisher));

        return httpSecurity.build();
    }
}