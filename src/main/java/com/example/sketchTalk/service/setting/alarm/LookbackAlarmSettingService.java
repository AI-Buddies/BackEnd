package com.example.sketchTalk.service.setting.alarm;

import com.example.sketchTalk.dto.setting.out.LookbackAlarmSettingRes;
import com.example.sketchTalk.model.entity.setting.alarm.LookbackAlarmSetting;
import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class LookbackAlarmSettingService {
    // TODO: 토큰 구현 후 수정
    LookbackAlarmSetting lookbackAlarmSetting = null;

    public LookbackAlarmSettingRes getLookbackAlarmSetting() {
        // 토큰 구현 후 관련 로직 추가하기

        boolean canAlarm = lookbackAlarmSetting.isCanAlarm();
        LocalTime alarmTime = lookbackAlarmSetting.getAlarmTime();
        AlarmUnit alarmUnit = lookbackAlarmSetting.getAlarmUnit();

        return new LookbackAlarmSettingRes(canAlarm, alarmTime, alarmUnit);
    }

}
