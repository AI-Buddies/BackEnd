package com.example.sketchTalk.model.entity.setting.alarm;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "lookbackalarmsetting")
public class LookbackAlarmSetting {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "can_alarm", nullable = false)
    private boolean canAlarm = true;

    @Column(name = "alarm_time", nullable = false)
    private LocalTime alarmTime = LocalTime.of(20, 0);

    @Enumerated(EnumType.STRING)
    @Column(name = "lookback_unit", nullable = false)
    private AlarmUnit alarmUnit = AlarmUnit.DAY;

    public LookbackAlarmSetting(Long userId) {
        this.userId = userId;
    }

    public void updateSetting(boolean canAlarm, LocalTime alarmTime, AlarmUnit alarmUnit) {
        this.canAlarm = canAlarm;
        this.alarmTime = alarmTime;
        this.alarmUnit = alarmUnit;
    }
}