package com.example.sketchTalk.dto.setting.in;

public record SetDefaultSettingReq(
        Long userId,
        boolean canAlarm
) {}
