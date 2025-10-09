package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;

public record GetAudioSettingRes(
   VoiceType voiceType,
   double voiceSpeed,
   Bgm bgm
) {
}
