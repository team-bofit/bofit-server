package org.sopt.bofit.global.oauth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@Getter
public class JwtProvider {

    private final Long accessTokenExpireMillis;
    private final Long refreshTokenExpireMillis;
    private final SecretKey secretKey;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.accessTokenExpiration}") Long accessExpiration,
            @Value("${jwt.refreshTokenExpiration}") Long refreshExpiration
    ){
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpireMillis = accessExpiration;
        this.refreshTokenExpireMillis = refreshExpiration;
    }

    public String generateAccessToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



}
