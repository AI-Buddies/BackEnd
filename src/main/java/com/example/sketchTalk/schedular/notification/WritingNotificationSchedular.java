package com.example.sketchTalk.schedular.notification;

import com.example.sketchTalk.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WritingNotificationSchedular {
    private final FcmService fcmService;
    // private final DefaultSettingRepository defaultSettingRepository;
    // private final WritingNotificationSettingRepository writingNotificationSettingRepository;

    // 매 분 0초마다 실행되는 스케쥴러
    // (초 / 분 / 시 / 일 / 월 / 요일)
    @Scheduled(cron = "0 * * * * *")
    public void sendNotifications() {

    }
}