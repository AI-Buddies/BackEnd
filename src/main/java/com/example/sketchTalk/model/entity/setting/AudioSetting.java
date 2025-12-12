package com.example.sketchTalk.model.entity.setting;

import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;
import com.example.sketchTalk.model.entity.setting.enums.VoiceTypeConverter;
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

    @Convert(converter = VoiceTypeConverter.class)
    @Column(name = "voice_type", nullable = false)
    private VoiceType voiceType = VoiceType.KO_KR_SEOHYEON_NEURAL;

    @Column(name = "voice_speed", nullable = false)
    private double voiceSpeed = 1.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Bgm bgm = Bgm.CALM;

    public AudioSetting(Long userId) {
        this.userId = userId;
    }

    public void updateSetting(VoiceType voiceType, double voiceSpeed, Bgm bgm) {
        this.voiceType = voiceType;
        this.voiceSpeed = voiceSpeed;
        this.bgm = bgm;
    }
}
