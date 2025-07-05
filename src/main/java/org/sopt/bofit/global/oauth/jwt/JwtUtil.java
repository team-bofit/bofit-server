package org.sopt.bofit.global.oauth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.constant.OAuthErrorCode;
import org.sopt.bofit.global.exception.custom_exception.InternalException;
import org.sopt.bofit.global.exception.custom_exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.*;
import static org.sopt.bofit.global.exception.constant.OAuthErrorCode.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProvider jwtProvider;

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProvider.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            log.info("🔍 받은 JWT 토큰: [{}]", token);
            getClaims(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new UnAuthorizedException(JWT_INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰입니다.");
            throw new UnAuthorizedException(JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 토큰입니다.");
            throw new UnAuthorizedException(JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰입니다.");
            throw new UnAuthorizedException(JWT_INVALID);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new InternalException(INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    public Long extractUserIdFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            log.warn("❌ JWT에서 userId 추출 실패: {}", e.getMessage());
            throw new UnAuthorizedException(JWT_USER_ID_EXTRACTION_FAILED);
        }
    }

}
