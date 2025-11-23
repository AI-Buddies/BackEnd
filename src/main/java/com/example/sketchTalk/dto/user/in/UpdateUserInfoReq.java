package com.example.sketchTalk.dto.user.in;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record UpdateUserInfoReq(
        String nickname,
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        String loginId,
        String password
) {
}
