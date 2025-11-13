package com.example.sketchTalk.dto.user.in;

import java.time.LocalDate;

public record UpdateUserInfoReq(
        String nickname,
        LocalDate birthdate,
        String loginId,
        String password
) {
}
