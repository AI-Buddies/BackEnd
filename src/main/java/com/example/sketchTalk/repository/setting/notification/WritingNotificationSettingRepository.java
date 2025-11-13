package com.example.sketchTalk.repository.setting.notification;

import com.example.sketchTalk.model.entity.setting.notification.WritingNotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WritingNotificationSettingRepository extends JpaRepository<WritingNotificationSetting, Long> {
    Optional<WritingNotificationSetting> findByUserId(Long userId);
}