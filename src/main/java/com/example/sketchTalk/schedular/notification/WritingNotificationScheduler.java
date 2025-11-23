package com.example.sketchTalk.schedular.notification;

import com.example.sketchTalk.model.entity.DeviceToken;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.model.entity.setting.notification.WritingNotificationSetting;
import com.example.sketchTalk.repository.DeviceTokenRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import com.example.sketchTalk.repository.setting.notification.WritingNotificationSettingRepository;
import com.example.sketchTalk.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class WritingNotificationScheduler {
    private final DeviceTokenRepository deviceTokenRepository;
    private final DefaultSettingRepository defaultSettingRepository;
    private final WritingNotificationSettingRepository writingNotificationSettingRepository;

    private final FcmService fcmService;

    // 매 분 0초마다 실행되는 스케쥴러
    // (초 / 분 / 시 / 일 / 월 / 요일)
    @Scheduled(cron = "0 * * * * *")
    public void sendNotifications() {
        LocalTime now = LocalTime.now().withSecond(0);

        // 알람 가능한 User List
        List<DefaultSetting> defaultSettings = defaultSettingRepository
                .findAllByCanAlarmIsTrue();

        // 작성 알림이 가능하고, 시간이 현재와 동일한 User List
        List<WritingNotificationSetting> writingNotificationSettings = writingNotificationSettingRepository.findAllByCanNotifyIsTrueAndNotificationTimeIs(now);

        if (defaultSettings.isEmpty() || writingNotificationSettings.isEmpty()) {
            return;
        }

        // 두 조건을 확인하고, 둘 다 동일하다면 작성 알림 푸쉬
        Set<Long> alarmEnabledUserIds = defaultSettings.stream()
                .map(DefaultSetting::getUserId)
                .collect(Collectors.toSet());

        writingNotificationSettings.stream()
                .filter(ws -> alarmEnabledUserIds.contains(ws.getUserId()))
                .forEach(ws -> {
                    Long userId = ws.getUserId();

                    // 해당 유저의 모든 기기 토큰 조회
                    List<DeviceToken> deviceTokens = deviceTokenRepository.findAllByUserIdAndRevokedFalse(userId);

                    if (deviceTokens.isEmpty()) {
                        return;
                    }

                    deviceTokens.forEach(deviceToken -> {
                        String token = deviceToken.getFcmToken();

                        try {
                            fcmService.sendToToken(
                                token,
                                    "오늘의 일기를 기록해보세요",
                                    "오늘의 일기를 작성하고, 그림으로 남겨볼까요?",
                                    null
                            );
                        } catch (FirebaseMessagingException e) {
                            log.warn("FCM 전송 실패 userId={}, token={}", userId, token, e);
                        }
                    });
                });

    }
}