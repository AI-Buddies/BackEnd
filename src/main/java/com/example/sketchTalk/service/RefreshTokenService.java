package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.refreshToken.RefreshReq;
import com.example.sketchTalk.dto.refreshToken.RefreshRes;
import com.example.sketchTalk.exception.token.RtExceptions;
import com.example.sketchTalk.model.entity.RefreshToken;
import com.example.sketchTalk.repository.RefreshTokenRepository;
import com.example.sketchTalk.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    public RefreshTokenService(
            @Value("${spring.refresh.duration.ms}")  Long refreshTokenDurationMs,
            RefreshTokenRepository refreshTokenRepository,
            JwtUtils jwtUtils
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
        this.jwtUtils = jwtUtils;
    }

    private void validateNotExpired(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            // 만료 토큰 청소
            refreshTokenRepository.deleteByUserId(refreshToken.getUserId());

            throw new CustomException(RtExceptions.INVALID_TOKEN);
        }
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        // 이미 있다면 삭제
        refreshTokenRepository.deleteByUserId(userId);

        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plusMillis(refreshTokenDurationMs);

        RefreshToken refreshToken = refreshTokenRepository.save(new RefreshToken(userId, token, expiryDate));

        return refreshToken;
    }

    @Transactional
    public RefreshRes reissueAccessToken(RefreshReq refreshReq) {
        // 1. 토큰 찾기
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshReq.refreshToken())
                .orElseThrow( () -> new CustomException(RtExceptions.INVALID_TOKEN));

        // 2. 만료일 확인
        validateNotExpired(refreshToken);

        // 3. JWT, Refresh 재발급
        Long userId = refreshToken.getUserId();
        String accessToken = jwtUtils.generateJwtToken(userId);
        refreshToken = createRefreshToken(userId);

        return new RefreshRes(accessToken, refreshToken.getToken());
    }

    @Transactional
    public Long deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }
}
