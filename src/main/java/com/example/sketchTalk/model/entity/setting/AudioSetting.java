package com.example.sketchTalk.model.entity.setting;

import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "audiosetting")
public class AudioSetting {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "voice_type", nullable = false)
    private VoiceType voiceType = VoiceType.ROBOT;

    @Column(name = "voice_speed", nullable = false)
    private double voiceSpeed = 1.0; // float을 써도 되지 않을까?

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Bgm bgm = Bgm.CALM;
}
