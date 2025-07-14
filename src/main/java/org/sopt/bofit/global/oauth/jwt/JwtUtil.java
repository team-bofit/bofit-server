package org.sopt.bofit.global.oauth.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.custom_exception.InternalException;
import org.sopt.bofit.global.exception.custom_exception.UnAuthorizedException;
import org.springframework.stereotype.Component;

import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.*;

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
            getClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new UnAuthorizedException(JWT_INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizedException(JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new UnAuthorizedException(JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new UnAuthorizedException(JWT_INVALID);
        } catch (Exception e) {
            log.warn("Unexpected JWT error: {}", e.getMessage());
            throw new InternalException(INTERNAL_SERVER_ERROR);
        }
    }

    public Long extractUserIdFromToken(String token) {
        try {
            Claims claims = getClaims(token);
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            throw new UnAuthorizedException(JWT_USER_ID_EXTRACTION_FAILED);
        }
    }

}
