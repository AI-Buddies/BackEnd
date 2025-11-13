package com.example.sketchTalk.dto.user.out;

import java.time.LocalDate;

public record UpdateUserInfoRes(
        String nickname,
        LocalDate birthdate
) {
}
