package com.example.sketchTalk.dto.setting.in;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;

import java.time.LocalTime;

public record SetPastNotificationSettingReq(
        boolean canNotify,
        LocalTime notificationTime,
        int notificationValue,
        AlarmUnit notificationUnit
) {
}
