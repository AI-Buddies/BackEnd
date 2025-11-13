package com.example.sketchTalk.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;

    public String sendToToken(
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

        return firebaseMessaging.send(message);
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