package com.example.sketchTalk.dto.setting.in;

import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;

public record SetRemainderAlarmSettingReq (
        Long userId,
        VoiceType voiceType,
        double voiceSpeed,
        Bgm bgm
) {
}