package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetAudioSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAudioSettingRes;
import com.example.sketchTalk.dto.setting.out.SettingRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
import org.springframework.stereotype.Service;

@Service
public class AudioSettingService {
    // TODO: 토큰 구현 후 수정
    private final AudioSettingRepository audioSettingRepository;

    public AudioSettingService(AudioSettingRepository audioSettingRepository) {
        this.audioSettingRepository = audioSettingRepository;
    }

    public GetAudioSettingRes getAudioSetting(GetSettingReq req) {
        AudioSetting audioSetting = audioSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        VoiceType voiceType = audioSetting.getVoiceType();
        double voiceSpeed = audioSetting.getVoiceSpeed();
        Bgm bgm = audioSetting.getBgm();

        return new GetAudioSettingRes(voiceType, voiceSpeed, bgm);
    }

    public SettingRes setAudioSetting(SetAudioSettingReq req) {
        AudioSetting audioSetting = audioSettingRepository.findByUserId(req.userId())
                        .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        audioSetting.updateVoiceType(req.voiceType());
        audioSetting.updateVoiceSpeed(req.voiceSpeed());
        audioSetting.updateBgm(req.bgm());

        audioSettingRepository.save(audioSetting);

        return new SettingRes("UPDATE_SUCCESS");
    }
}
