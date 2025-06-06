package com.example.sketchTalk.repository.setting.alarm;

import com.example.sketchTalk.model.entity.setting.alarm.LookbackAlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LookbackAlarmSettingRepository extends JpaRepository<LookbackAlarmSetting, Long> {
    Optional<LookbackAlarmSetting> findByUserId(Long userId);
}
