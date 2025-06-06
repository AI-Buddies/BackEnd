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
@Table(name = "remaindersetting")
public class RemainderAlarmSetting {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "can_alarm", nullable = false)
    private boolean canAlarm = true;

    @Column(name = "alarm_time", nullable = false)
    private LocalTime alarmTime = LocalTime.of(20, 0);

    @Column(name = "alarm_value", nullable = false)
    private int alarmValue = 1;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_unit", nullable = false)
    private AlarmUnit alarmUnit = AlarmUnit.DAY;
}
