package com.example.sketchTalk.dto.user.in;

public record ChangeNicknameReq(
        String loginId,
        String password,
        String newNickname
) {
}