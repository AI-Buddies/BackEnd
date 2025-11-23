package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record GetPastNotificationSettingRes(
        boolean canNotify,
        @JsonFormat(shape = JsonFormat.Shape.BOOLEAN, pattern = "HH:mm")
        LocalTime notificationTime,
        AlarmUnit notificationUnit
) {
}
