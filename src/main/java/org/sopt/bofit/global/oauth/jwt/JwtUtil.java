package org.sopt.bofit.global.oauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;
import org.sopt.bofit.global.exception.custom_exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 JWT: {}", e.getMessage());
            return false;
        }
    }

    public Long extractUserIdFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            log.warn("❌ JWT에서 userId 추출 실패: {}", e.getMessage());
            throw new UnAuthorizedException(OAuthErrorCode.JWT_USER_ID_EXTRACTION_FAILED);
        }
    }

}
