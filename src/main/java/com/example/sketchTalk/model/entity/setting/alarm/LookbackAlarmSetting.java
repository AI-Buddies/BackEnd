package com.example.sketchTalk.model.entity.setting.alarm;

import com.example.sketchTalk.model.entity.setting.enums.AlarmUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private boolean canAlarm;

    @Column(name = "alarm_time", nullable = false)
    private LocalTime alarmTime = LocalTime.of(20, 0);

    @Column(name = "lookback_unit", nullable = false)
    private AlarmUnit alarmUnit = AlarmUnit.DAY;
}
