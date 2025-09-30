package com.example.sketchTalk.dto.setting.out;

import java.time.LocalDate;

public record GetProfileRes(
        String nickname,
        LocalDate birthdate,
        boolean canAlarm
) {
}