package com.example.sketchTalk.model.entity.setting.notification;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pastnotificationsetting")
public class PastNotificationSetting {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "can_notify", nullable = false)
    private boolean canNotify = true;

    @Column(name = "notification_time", nullable = false)
    private LocalTime notificationTime = LocalTime.of(20, 0);

    @Column(name = "notification_value", nullable = false)
    private int notificationValue = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_unit", nullable = false)
    private AlarmUnit notificationUnit = AlarmUnit.DAY;

    public PastNotificationSetting(Long userId) {
        this.userId = userId;
    }

    public void updateSetting(boolean canAlarm, LocalTime alarmTime, int alarmValue, AlarmUnit alarmUnit) {
        this.canNotify = canAlarm;
        this.notificationTime = alarmTime;
        this.notificationValue = alarmValue;
        this.notificationUnit = alarmUnit;
    }
}