package com.example.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                /*.authorizeExchange(ex -> ex
                        .pathMatchers(
                                "/actuator/**", // Actuator open
                                "/tasks/api/v1/auth/**",   // Login/Register open
                                "/swagger-ui/**",         // Swagger open
                                "/v3/api-docs/**"         // API docs open
                        ).permitAll()
                        // ❌ All other endpoints require JWT
                        .anyExchange().authenticated()
                )*/
                .authorizeExchange(ex -> ex
                        .anyExchange().permitAll()   //  IMPORTANT
                )
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
