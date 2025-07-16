package org.sopt.bofit.global.oauth.jwt;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.*;
import static org.sopt.bofit.global.oauth.constant.JwtExceptionConstants.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.sopt.bofit.global.exception.custom_exception.CustomException;
import org.sopt.bofit.global.exception.custom_exception.UnAuthorizedException;
import org.sopt.bofit.global.oauth.constant.HttpHeaderConstants;
import org.sopt.bofit.global.oauth.constant.RequestAttributeConstants;
import org.sopt.bofit.global.oauth.constant.SwaggerPathConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> EXCLUDED_PATH_PREFIXES = List.of(
            SwaggerPathConstants.SWAGGER_CONFIG,
            SwaggerPathConstants.SWAGGER_UI,
            SwaggerPathConstants.SWAGGER_DOCS
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if(token == null){
            request.setAttribute(RequestAttributeConstants.EXCEPTION, NOT_EXIST);
            throw new UnAuthorizedException(JWT_NOT_FOUND);
        }
        try {
            if (jwtUtil.isTokenValid(token)) {
                Authentication authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException e) {
            request.setAttribute(RequestAttributeConstants.EXCEPTION, e.getErrorCode());
            throw e;
        }

        filterChain.doFilter(request, response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return EXCLUDED_PATH_PREFIXES.stream().anyMatch(uri::startsWith);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaderConstants.AUTHORIZATION);
        String validTokenPrefix = HttpHeaderConstants.BEARER_PREFIX;
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
