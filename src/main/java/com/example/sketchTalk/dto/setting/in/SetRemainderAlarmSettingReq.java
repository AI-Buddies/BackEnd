package com.example.sketchTalk.dto.setting.in;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;

import java.time.LocalTime;

public record SetRemainderAlarmSettingReq(
        boolean canAlarm,
        LocalTime alarmTime,
        int alarmValue,
        AlarmUnit alarmUnit
) {
}