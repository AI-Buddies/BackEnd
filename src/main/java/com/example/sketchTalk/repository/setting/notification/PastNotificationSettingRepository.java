package com.example.sketchTalk.repository.setting.notification;

import com.example.sketchTalk.model.entity.setting.notification.PastNotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PastNotificationSettingRepository extends JpaRepository<PastNotificationSetting, Long> {
    Optional<PastNotificationSetting> findByUserId(Long userId);
}
