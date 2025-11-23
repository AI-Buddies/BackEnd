package com.example.sketchTalk.dto.setting.in;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record SetWritingNotificationSettingReq(
        boolean canNotify,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime notificationTime,
        AlarmUnit notificationUnit
) {
}