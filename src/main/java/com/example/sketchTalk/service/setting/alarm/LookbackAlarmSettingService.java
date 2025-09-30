package com.example.sketchTalk.service.setting.alarm;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.out.GetLookbackAlarmSettingRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.alarm.LookbackAlarmSetting;
import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.example.sketchTalk.repository.setting.alarm.LookbackAlarmSettingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class LookbackAlarmSettingService {
    private final LookbackAlarmSettingRepository lookbackAlarmSettingRepository;

    public LookbackAlarmSettingService(LookbackAlarmSettingRepository lookbackAlarmSettingRepository) {
        this.lookbackAlarmSettingRepository = lookbackAlarmSettingRepository;
    }

    public GetLookbackAlarmSettingRes getLookbackAlarmSetting(GetSettingReq req) {
        // 토큰 구현 후 관련 로직 추가하기
        LookbackAlarmSetting  lookbackAlarmSetting = lookbackAlarmSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        boolean canAlarm = lookbackAlarmSetting.isCanAlarm();
        LocalTime alarmTime = lookbackAlarmSetting.getAlarmTime();
        AlarmUnit alarmUnit = lookbackAlarmSetting.getAlarmUnit();

        return new GetLookbackAlarmSettingRes(canAlarm, alarmTime, alarmUnit);
    }
}
