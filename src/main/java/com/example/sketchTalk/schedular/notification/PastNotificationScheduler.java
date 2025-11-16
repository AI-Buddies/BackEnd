package com.example.sketchTalk.schedular.notification;

import com.example.sketchTalk.model.entity.DeviceToken;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.model.entity.setting.notification.PastNotificationSetting;
import com.example.sketchTalk.repository.DeviceTokenRepository;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import com.example.sketchTalk.repository.setting.notification.PastNotificationSettingRepository;
import com.example.sketchTalk.service.FcmService;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PastNotificationScheduler {
    private final DeviceTokenRepository deviceTokenRepository;
    private final DefaultSettingRepository defaultSettingRepository;
    private final PastNotificationSettingRepository pastNotificationSettingRepository;
    private final DiaryRepository diaryRepository;

    private final FcmService fcmService;

    // 매 분 0초마다 실행되는 스케쥴러
    // (초 / 분 / 시 / 일 / 월 / 요일)
    @Scheduled(cron = "0 * * * * *")
    public void sendNotifications() {
        LocalTime now = LocalTime.now().withSecond(0);
        LocalDate today = LocalDate.now();

        // 알림 가능한 User List
        List<DefaultSetting> defaultSettings = defaultSettingRepository
                .findAllByCanAlarmIsTrue();

        // 과거 알림이 가능하고, 시간이 현재와 동일한 User List
        List<PastNotificationSetting> pastNotificationSettings = pastNotificationSettingRepository
                .findAllByCanNotifyIsTrueAndNotificationTimeIs(now);

        if (defaultSettings.isEmpty() || pastNotificationSettings.isEmpty()) {
            return;
        }

        // 두 조건 확인 후 userId 추출
        Set<Long> alarmEnabledUserIds = defaultSettings.stream()
                .map(DefaultSetting::getUserId)
                .collect(Collectors.toSet());

        pastNotificationSettings.stream()
                .filter(pn -> alarmEnabledUserIds.contains(pn.getUserId()))
                .forEach(pn -> {
                    // 유저의 설정값에 맞는 과거 일기 조회
                    Long userId = pn.getUserId();
                    LocalDate pastDate = switch (pn.getNotificationUnit()) {
                        case DAY -> pastDate = today.minusDays(pn.getNotificationValue());
                        case WEEK -> pastDate = today.minusWeeks(pn.getNotificationValue());
                        case MONTH -> pastDate = today.minusMonths(pn.getNotificationValue());
                    };

                    List<Diary> diaries = diaryRepository.findAllByUserIdAndDateIs(userId, pastDate);

                    if (diaries.isEmpty()) {
                        return;
                    }

                    // 해당 유저의 모든 기기 토큰 조회
                    List<DeviceToken> deviceTokens = deviceTokenRepository.findAllByUserId(userId);

                    if (deviceTokens.isEmpty()) {
                        return;
                    }

                    Map<String, String> data = Map.of(
                            "year", String.valueOf(pastDate.getYear()),
                            "month", String.valueOf(pastDate.getMonthValue())
                    );

                    deviceTokens.forEach(deviceToken -> {
                        String token = deviceToken.getFcmToken();

                        try {
                            fcmService.sendToToken(
                                    token,
                                    "과거의 일기를 열람하세요.",
                                    "그림과 함께 과거의 일기를 확인해볼까요?",
                                    data
                            );
                        } catch (FirebaseMessagingException e) {
                            log.warn("FCM 전송 실패 userId={}, token={}", userId, token, e);
                        }
                    });
                });

    }
}