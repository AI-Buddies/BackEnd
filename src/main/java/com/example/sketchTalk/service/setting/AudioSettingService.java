package com.example.sketchTalk.service.setting;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.setting.in.SetAudioSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAudioSettingRes;
import com.example.sketchTalk.dto.setting.out.SetAudioSettingRes;
import com.example.sketchTalk.exception.setting.SettingExceptions;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.model.entity.setting.enums.Bgm;
import com.example.sketchTalk.model.entity.setting.enums.VoiceType;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AudioSettingService {
    private final AudioSettingRepository audioSettingRepository;

    public AudioSettingService(AudioSettingRepository audioSettingRepository) {
        this.audioSettingRepository = audioSettingRepository;
    }

    public GetAudioSettingRes getAudioSetting(Long userId) {
        AudioSetting audioSetting = audioSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        VoiceType voiceType = audioSetting.getVoiceType();
        double voiceSpeed = audioSetting.getVoiceSpeed();
        Bgm bgm = audioSetting.getBgm();

        return new GetAudioSettingRes(voiceType, voiceSpeed, bgm);
    }

    @Transactional
    public SetAudioSettingRes setAudioSetting(Long userId, SetAudioSettingReq req) {
        AudioSetting audioSetting = audioSettingRepository.findByUserId(userId)
                        .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        // 설정값 유효성 체크
        if (req.voiceSpeed() <= 0.0 || 2.0 < req.voiceSpeed()) {
            throw new CustomException(SettingExceptions.INVALID_VALUE);
        }

        audioSetting.updateSetting(req.voiceType(), req.voiceSpeed(), req.bgm());
        audioSettingRepository.save(audioSetting);

        SetAudioSettingRes setAudioSettingRes = SetAudioSettingRes.builder()
                .voiceType(req.voiceType())
                .voiceSpeed(req.voiceSpeed())
                .bgm(req.bgm())
                .build();

        return setAudioSettingRes;
    }
}