package com.example.sketchTalk.controller.setting;

import com.example.sketchTalk.dto.setting.in.SetAudioSettingReq;
import com.example.sketchTalk.dto.setting.out.AudioSettingRes;
import com.example.sketchTalk.dto.setting.out.SettingRes;
import com.example.sketchTalk.service.setting.AudioSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PatchMapping("/setting/tts")
    public ResponseEntity<SettingRes> setAudioSetting(@RequestBody SetAudioSettingReq req) {
        SettingRes settingRes = audioSettingService.setAudioSetting(req);

        return ResponseEntity.ok(settingRes);
    }
}
