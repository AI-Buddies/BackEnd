package com.example.sketchTalk.dto.setting.out;

import lombok.Builder;

@Builder
public record SetDefaultSettingRes (
        Long userId,
        boolean canAlarm
) {}
