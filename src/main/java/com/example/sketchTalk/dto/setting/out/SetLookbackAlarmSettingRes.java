package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record SetLookbackAlarmSettingRes(
        Long userId,
        boolean canAlarm,
        LocalTime alarmTime,
        AlarmUnit alarmUnit
) {
}
