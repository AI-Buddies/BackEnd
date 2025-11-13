package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.model.entity.setting.notification.PastNotificationSetting;
import com.example.sketchTalk.model.entity.setting.notification.WritingNotificationSetting;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import com.example.sketchTalk.repository.setting.notification.PastNotificationSettingRepository;
import com.example.sketchTalk.repository.setting.notification.WritingNotificationSettingRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingProvisioningService {
    private final AudioSettingRepository audioSettingRepository;
    private final DefaultSettingRepository defaultSettingRepository;
    private final PastNotificationSettingRepository pastNotificationSettingRepository;
    private final WritingNotificationSettingRepository writingNotificationSettingRepository;

    // UserService.register() 에서만 호출
    @Transactional(propagation = Propagation.MANDATORY)
    public void provisionDefaultSetting(Long userId) {
        audioSettingRepository.save(new AudioSetting(userId));
        defaultSettingRepository.save(new DefaultSetting(userId));
        pastNotificationSettingRepository.save(new PastNotificationSetting(userId));
        writingNotificationSettingRepository.save(new WritingNotificationSetting(userId));
    }

    //UserService.delete() 에서만 호출
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteUserSetting(Long userId) {
        audioSettingRepository.deleteById(userId);
        defaultSettingRepository.deleteById(userId);
        pastNotificationSettingRepository.deleteById(userId);
        writingNotificationSettingRepository.deleteById(userId);
    }
}
