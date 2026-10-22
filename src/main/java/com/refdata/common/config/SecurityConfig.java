package com.refdata.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final String ALLERGIES = "/api/v1/allergies/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health/**", "/error").permitAll()
                        .requestMatchers(HttpMethod.GET, ALLERGIES).hasAuthority("SCOPE_allergy:read")
                        .requestMatchers(HttpMethod.POST, ALLERGIES).hasAuthority("SCOPE_allergy:write")
                        .requestMatchers(HttpMethod.PUT, ALLERGIES).hasAuthority("SCOPE_allergy:write")
                        .requestMatchers(HttpMethod.DELETE, ALLERGIES).hasAuthority("SCOPE_allergy:write")
                        .anyRequest().denyAll())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
