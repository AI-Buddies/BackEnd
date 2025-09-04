package com.example.sketchTalk.dto.setting.out;

import java.time.LocalDate;

public record DefaultSettingRes (
        String nickname,
        LocalDate birthdate
) {
}
