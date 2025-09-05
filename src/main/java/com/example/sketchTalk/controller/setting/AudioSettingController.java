package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk.dto.setting.out.AudioSettingRes;
import com.example.sketchTalk.service.setting.AudioSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AudioSettingController {
    private final AudioSettingService audioSettingService;

    public AudioSettingController(AudioSettingService audioSettingService) {
        this.audioSettingService = audioSettingService;
    }

    @GetMapping("/setting/tts")
    public ResponseEntity<AudioSettingRes> getAudioSetting() {
        AudioSettingRes audioSettingRes = audioSettingService.getAudioSetting();

        return ResponseEntity.ok(audioSettingRes);
    }
}
