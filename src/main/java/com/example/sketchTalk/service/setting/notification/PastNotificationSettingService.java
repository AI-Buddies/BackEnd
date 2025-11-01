package com.example.sketchTalk.service.setting.notification;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.setting.in.SetPastNotificationSettingReq;
import com.example.sketchTalk.dto.setting.out.GetPastNotificationSettingRes;
import com.example.sketchTalk.dto.setting.out.SetPastNotificationSettingRes;
import com.example.sketchTalk.exception.setting.SettingExceptions;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.notification.PastNotificationSetting;
import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import com.example.sketchTalk.repository.setting.notification.PastNotificationSettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class PastNotificationSettingService {
    private final PastNotificationSettingRepository pastNotificationSettingRepository;

    public PastNotificationSettingService(PastNotificationSettingRepository pastNotificationSettingRepository) {
        this.pastNotificationSettingRepository = pastNotificationSettingRepository;
    }

    public GetPastNotificationSettingRes getPastNotificationSetting(Long userId) {
        PastNotificationSetting pastNotificationSetting = pastNotificationSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        boolean canNotify = pastNotificationSetting.isCanNotify();
        LocalTime notificationTime = pastNotificationSetting.getNotificationTime();
        AlarmUnit notificationUnit = pastNotificationSetting.getNotificationUnit();

        return new GetPastNotificationSettingRes(canNotify, notificationTime, notificationUnit);
    }

    @Transactional
    public SetPastNotificationSettingRes setPastNotificationSetting(Long userId, SetPastNotificationSettingReq req) {
        PastNotificationSetting pastNotificationSetting = pastNotificationSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        if (req.notificationValue() < 1 || 10 < req.notificationValue()) {
            throw new CustomException(SettingExceptions.INVALID_VALUE);
        }

        pastNotificationSetting.updateSetting(req.canNotify(), req.notificationTime(), req.notificationValue(), req.notificationUnit());
        pastNotificationSettingRepository.save(pastNotificationSetting);

        SetPastNotificationSettingRes setPastNotificationSettingRes = SetPastNotificationSettingRes.builder()
                .canNotify(req.canNotify())
                .notificationTime(req.notificationTime())
                .notificationValue(req.notificationValue())
                .notificationUnit(req.notificationUnit())
                .build();

        return setPastNotificationSettingRes;
    }
}