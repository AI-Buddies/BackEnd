package com.example.sketchTalk.security.jwt;

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
    // 실패하면 AuthenticationException을 던진다.
    public void validateJwtToken(String token) throws InvalidJwtAuthenticationException {
        try {
            parser.parseClaimsJws(token);

        } catch (MalformedJwtException e) {
            throw new InvalidJwtAuthenticationException("잘못된 JWT 형식입니다.", e);

        } catch (ExpiredJwtException e) {
            throw new InvalidJwtAuthenticationException("JWT 토큰이 만료되었습니다.", e);

        } catch (UnsupportedJwtException e) {
            throw new InvalidJwtAuthenticationException("지원하지 않는 JWT 형식입니다.", e);

        } catch (IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("JWT 클레임이 비어 있습니다.", e);

        } catch (JwtException e) {
            throw new InvalidJwtAuthenticationException("JWT 검증 실패", e);

        }
    }
}