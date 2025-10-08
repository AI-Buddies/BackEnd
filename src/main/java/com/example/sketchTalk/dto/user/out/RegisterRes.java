package com.example.sketchTalk.dto.user.out;

public record RegisterRes(
        String accessToken,
        String refreshToken
) {
}
