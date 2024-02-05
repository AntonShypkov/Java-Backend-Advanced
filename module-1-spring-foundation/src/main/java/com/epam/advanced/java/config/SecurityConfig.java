package com.epam.advanced.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String AUTHORIZATION_ENDPOINT_MATCHER = "/actuator/**";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTHORIZATION_ENDPOINT_MATCHER)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .build();
    }
}
