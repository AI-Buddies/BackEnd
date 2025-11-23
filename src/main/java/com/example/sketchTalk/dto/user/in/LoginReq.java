package com.example.sketchTalk.dto.user.in;

public record LoginReq(
        String loginId,
        String password,
        String deviceToken,
        String deviceType,
        String deviceIdentifier
) {
}