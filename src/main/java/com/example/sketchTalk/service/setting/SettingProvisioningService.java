package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.model.entity.setting.alarm.LookbackAlarmSetting;
import com.example.sketchTalk.model.entity.setting.alarm.RemainderAlarmSetting;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import com.example.sketchTalk.repository.setting.alarm.LookbackAlarmSettingRepository;
import com.example.sketchTalk.repository.setting.alarm.RemainderAlarmSettingRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingProvisioningService {
    private final AudioSettingRepository audioSettingRepository;
    private final DefaultSettingRepository defaultSettingRepository;
    private final LookbackAlarmSettingRepository lookbackAlarmSettingRepository;
    private final RemainderAlarmSettingRepository remainderAlarmSettingRepository;

    // UserService.register() 에서만 호출
    @Transactional(propagation = Propagation.MANDATORY)
    public void provisionDefaultSetting(Long userId) {
        audioSettingRepository.save(new AudioSetting(userId));
        defaultSettingRepository.save(new DefaultSetting(userId));
        lookbackAlarmSettingRepository.save(new LookbackAlarmSetting(userId));
        remainderAlarmSettingRepository.save(new RemainderAlarmSetting(userId));
    }
}
