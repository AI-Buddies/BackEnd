package com.example.sketchTalk.security.jwt;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.exception.token.JwtExceptions;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String jwtSecret;
    private final Long jwtExpirationInMs;
    private final JwtParser parser;

    private final Key key;

    public JwtUtils(
            @Value("${spring.jwt.secret}") String jwtSecret,
            @Value("${spring.jwt.expiration.ms}") Long jwtExpirationInMs
    ) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));

        this.parser = Jwts.parserBuilder()
                .setSigningKey(this.key)
                .build();
    }

    // 토큰 제공
    public String generateJwtToken(Long userId) {
        return Jwts.builder()
                // Payload
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))

                // Signature
                .signWith(key, SignatureAlgorithm.HS256)

                .compact();
    }

    // 토큰 -> userId 추출
    public Long getUserIdFromJwtToken(String token) {
        return Long.valueOf(
                parser.parseClaimsJws(token).getBody().getSubject()
        );
    }

    // 토큰 검증
    // 유효하면 아무것도 반환하지 않고 통과
    // 실패하면 CustomException을 던진다.
    public void validateJwtToken(String token) throws CustomException {
        try {
            parser.parseClaimsJws(token);

        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(JwtExceptions.from(e));
        }
    }
}