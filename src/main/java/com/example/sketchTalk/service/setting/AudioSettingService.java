package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.dto.setting.out.AudioSettingRes;
import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
import org.springframework.stereotype.Service;

@Service
public class AudioSettingService {
    // TODO: 토큰 구현 후 수정
    AudioSetting audioSetting = null;

    public AudioSettingRes getAudioSetting() {
        // 토큰 구현 후 관련 로직 추가하기
        VoiceType voiceType = audioSetting.getVoiceType();
        double voiceSpeed = audioSetting.getVoiceSpeed();
        Bgm bgm = audioSetting.getBgm();

        return new AudioSettingRes(voiceType, voiceSpeed, bgm);
    }
}
