package com.example.sketchTalk.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "devicetoken")
public class DeviceToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String fcmToken;

    private String deviceType;

    private String deviceIdentifier;

    @Column(nullable = false)
    private boolean revoked;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public DeviceToken(Long userId, String fcmToken, String deviceType, String deviceIdentifier) {
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.deviceType = deviceType;
        this.deviceIdentifier = deviceIdentifier;
        this.revoked = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateFcmToken(String newToken) {
        this.fcmToken = newToken;
        this.updatedAt = LocalDateTime.now();
    }

    public void revoke() {
        this.revoked = true;
        this.updatedAt = LocalDateTime.now();
    }
}
