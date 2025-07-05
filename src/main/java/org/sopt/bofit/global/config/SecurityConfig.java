package org.sopt.bofit.global.config;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.oauth.jwt.CustomAccessDeniedHandler;
import org.sopt.bofit.global.oauth.jwt.CustomAuthenticationEnrtyPoint;
import org.sopt.bofit.global.oauth.jwt.JwtAuthenticationFilter;
import org.sopt.bofit.global.oauth.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    private final String[] ALLOWED_PATHS = {
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**",
            "/oauth/kakao/login"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
//                        .requestMatchers(ALLOWED_PATHS).permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll() // 개발을 위해 일시적으로 허용
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(new CustomAuthenticationEnrtyPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                );

        return http.build();
    }
}
