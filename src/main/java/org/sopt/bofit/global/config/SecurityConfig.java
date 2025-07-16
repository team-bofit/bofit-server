package org.sopt.bofit.global.config;


import lombok.RequiredArgsConstructor;

import org.sopt.bofit.global.oauth.jwt.CustomAccessDeniedHandler;
import org.sopt.bofit.global.oauth.jwt.CustomAuthenticationEntryPoint;
import org.sopt.bofit.global.oauth.jwt.JwtAuthenticationFilter;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public static final String[] ALLOWED_PATHS = {
        "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-config",
        "/oauth/kakao/login",
        "/actuator/health",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                       .requestMatchers(ALLOWED_PATHS).permitAll()
                       .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                );

        return http.build();
    }
}
