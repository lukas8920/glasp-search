package org.kehrbusch;

import org.kehrbusch.failures.LoginAttemptService;
import org.kehrbusch.failures.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
            httpSecurity.authorizeRequests()
                    .antMatchers("/api-docs").permitAll()
                    .antMatchers("/api-docs.yaml").permitAll()
                    .antMatchers("/h2-console/**/**").permitAll()
                    .antMatchers("/data/init/zipstreet").permitAll()
                    .antMatchers("/data/init/city").permitAll()
                    .antMatchers("/app/search/zipstreet").permitAll()
                    .antMatchers("/app/search/zipstreetcity").permitAll();
        }

        if (profile.equals("prod")){
            httpSecurity.authorizeRequests()
                    .antMatchers("/api-docs").denyAll()
                    .antMatchers("/api-docs.yaml").denyAll();
        }

        httpSecurity.authorizeRequests()
                .antMatchers("/data/login").permitAll()
                .antMatchers("/auth/token/access").permitAll()
                .anyRequest().authenticated();

        httpSecurity.apply(new JwtTokenFilterConfigurer(jwtTokenProvider, loginAttemptService, publisher));

        return httpSecurity.build();
    }
}