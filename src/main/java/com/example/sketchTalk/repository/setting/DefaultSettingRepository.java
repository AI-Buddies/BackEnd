package com.example.sketchTalk.repository.setting;

import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DefaultSettingRepository extends JpaRepository<DefaultSetting, Long> {
    Optional<DefaultSetting> findByUserId(Long userId);

    List<DefaultSetting> findAllByCanAlarmIsTrue();
}
