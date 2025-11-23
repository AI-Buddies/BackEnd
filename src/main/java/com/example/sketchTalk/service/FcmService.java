package com.example.sketchTalk.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendTestMessage(String targetToken) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(targetToken)
                .setNotification(Notification.builder()
                        .setTitle("서버 FCM 테스트")
                        .setBody("백엔드에서 쏜 테스트 알림")
                        .build())
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        // 성공하면 messageId 형태 string 들어옴
        log.info("FCM send response: {}", response);
    }

    public void sendToToken(
            String token,
            String title,
            String body,
            Map<String, String> data
    ) throws FirebaseMessagingException {
        validateToken(token);
        validateTitle(title);
        validateBody(body);

        Message.Builder messageBuilder = Message.builder()
                .setToken(token);

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        if (data != null && !data.isEmpty()) {
            messageBuilder.putAllData(data);
        }

        Message message = messageBuilder
                .setNotification(notification)
                .build();

        firebaseMessaging.send(message);
    }

    private void validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("token is null or blank");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is null or blank");
        }
    }

    private void validateBody(String body) {
        if (body == null || body.isBlank()) {
            throw new IllegalArgumentException("body is null or blank");
        }
    }
}