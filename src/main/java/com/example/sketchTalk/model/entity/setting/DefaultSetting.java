package com.example.sketchTalk.model.entity.setting;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "defaultsetting")
public class DefaultSetting {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "can_alarm", nullable = false)
    private boolean canAlarm = true;

    public void toggleCanAlarm() {
        this.canAlarm = !this.canAlarm;
    }
}
