package com.example.sketchTalk.repository.setting;

import com.example.sketchTalk.model.entity.setting.AudioSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AudioSettingRepository extends JpaRepository<AudioSetting, Long> {
    Optional<AudioSetting> findByUserId(Long userId);
}
