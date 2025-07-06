package org.sopt.bofit.global.oauth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.custom_exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if(token != null) {
            try {
                if (jwtUtil.isTokenValid(token)) {
                    Authentication authentication = getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (CustomException e) {
                request.setAttribute("exception", e.getErrorCode());
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/swagger-ui") || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-config");
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("authorization");
        String validTokenPrefix = "Bearer ";
        if (authorization == null || !authorization.startsWith(validTokenPrefix)) {
            return null;
        }
        return authorization.substring(validTokenPrefix.length()).trim();
    }

    private Authentication getAuthentication(String token) {
        Long userId = jwtUtil.extractUserIdFromToken(token);
        return new JwtTokenAuthentication(userId);
    }

}
