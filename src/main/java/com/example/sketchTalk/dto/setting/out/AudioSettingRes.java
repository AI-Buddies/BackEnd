package com.example.sketchTalk.dto.setting.out;

import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;

public record AudioSettingRes(
   VoiceType voiceType,
   double voiceSpeed,
   Bgm bgm
) {
}
