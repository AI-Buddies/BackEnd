package com.example.sketchTalk.service.setting.alarm;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.out.GetRemainderAlarmSettingRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.alarm.RemainderAlarmSetting;
import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.example.sketchTalk.repository.setting.alarm.RemainderAlarmSettingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class RemainderAlarmSettingService {
    private final RemainderAlarmSettingRepository remainderAlarmSettingRepository;

    public RemainderAlarmSettingService(RemainderAlarmSettingRepository remainderAlarmSettingRepository) {
        this.remainderAlarmSettingRepository = remainderAlarmSettingRepository;
    }

    public GetRemainderAlarmSettingRes getRemainderAlarmSetting(GetSettingReq req) {
        RemainderAlarmSetting remainderAlarmSetting = remainderAlarmSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        boolean canAlarm = remainderAlarmSetting.isCanAlarm();
        LocalTime alarmTime = remainderAlarmSetting.getAlarmTime();
        int alarmValue = remainderAlarmSetting.getAlarmValue();
        AlarmUnit alarmUnit = remainderAlarmSetting.getAlarmUnit();

        return new GetRemainderAlarmSettingRes(canAlarm, alarmTime, alarmValue, alarmUnit);
    }
}