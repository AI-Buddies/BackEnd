package com.example.sketchTalk.dto.user.out;

public record LoginRes(
        String nickname,
        String accessToken,
        String refreshToken
) {
}
