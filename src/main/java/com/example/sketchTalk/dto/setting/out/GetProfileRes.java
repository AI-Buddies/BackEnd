package com.example.sketchTalk.dto.setting.out;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record GetProfileRes(
        String nickname,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        boolean canAlarm
) {
}