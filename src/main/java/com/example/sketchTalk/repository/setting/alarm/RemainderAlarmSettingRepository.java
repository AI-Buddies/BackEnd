package com.example.sketchTalk.repository.setting.alarm;

import com.example.sketchTalk.model.entity.setting.alarm.RemainderAlarmSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RemainderAlarmSettingRepository extends JpaRepository<RemainderAlarmSetting, Long> {
    Optional<RemainderAlarmSetting> findByUserId(Long userId);
}