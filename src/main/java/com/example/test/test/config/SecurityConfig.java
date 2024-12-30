package com.example.test.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF si no es necesario
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/book/**").permitAll() // Permitir acceso público a /book
                .anyRequest().authenticated() // Requerir autenticación para otras rutas
            );

        return http.build();
    }
}
