package com.example.sketchTalk.dto.user.out;

public record RegisterRes(
        String nickname,
        String accessToken,
        String refreshToken
) {
}
