package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;

import java.time.LocalTime;

public record GetRemainderAlarmSettingRes(
        boolean canAlarm,
        LocalTime alarmTime,
        int alarmValue,
        AlarmUnit alarmUnit
) {
}
