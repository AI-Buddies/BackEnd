package com.example.sketchTalk.service.setting.notification;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.setting.in.SetWritingNotificationSettingReq;
import com.example.sketchTalk.dto.setting.out.GetWritingNotificationSettingRes;
import com.example.sketchTalk.dto.setting.out.SetWritingNotificationSettingRes;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.notification.WritingNotificationSetting;
import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.example.sketchTalk.repository.setting.notification.WritingNotificationSettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class WritingNotificationSettingService {
    private final WritingNotificationSettingRepository writingNotificationSettingRepository;

    public WritingNotificationSettingService(WritingNotificationSettingRepository writingNotificationSettingRepository) {
        this.writingNotificationSettingRepository = writingNotificationSettingRepository;
    }

    @Transactional
    public GetWritingNotificationSettingRes getWritingNotificationSetting(Long userId) {
        WritingNotificationSetting writingNotificationSetting = writingNotificationSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        boolean canNotify = writingNotificationSetting.isCanNotify();
        LocalTime notificationTime = writingNotificationSetting.getNotificationTime();
        AlarmUnit notificationUnit = writingNotificationSetting.getNotificationUnit();

        return new GetWritingNotificationSettingRes(canNotify, notificationTime, notificationUnit);
    }

    @Transactional
    public SetWritingNotificationSettingRes setWritingNotificationSetting(Long userId, SetWritingNotificationSettingReq req) {
        WritingNotificationSetting writingNotificationSetting = writingNotificationSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        writingNotificationSetting.updateSetting(req.canNotify(), req.notificationTime(), req.notificationUnit());
        writingNotificationSettingRepository.save(writingNotificationSetting);

        SetWritingNotificationSettingRes setWritingNotificationSettingRes = SetWritingNotificationSettingRes.builder()
                .canNotify(req.canNotify())
                .notificationTime(req.notificationTime())
                .notificationUnit(req.notificationUnit())
                .build();

        return setWritingNotificationSettingRes;
    }
}