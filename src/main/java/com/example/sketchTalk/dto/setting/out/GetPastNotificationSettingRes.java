package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;

import java.time.LocalTime;

public record GetPastNotificationSettingRes(
        boolean canNotify,
        LocalTime notificationTime,
        AlarmUnit notificationUnit
) {
}
