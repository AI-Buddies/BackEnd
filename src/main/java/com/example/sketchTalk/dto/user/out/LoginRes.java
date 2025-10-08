package com.example.sketchTalk.dto.user.out;

public record LoginRes(
        String accessToken,
        String refreshToken
) {
}
