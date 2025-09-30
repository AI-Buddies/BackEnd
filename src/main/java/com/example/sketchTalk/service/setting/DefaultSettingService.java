package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetDefaultSettingReq;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.dto.setting.out.SetDefaultSettingRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.repository.UserRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultSettingService {
    private final UserRepository userRepository;
    private final DefaultSettingRepository defaultSettingRepository;

    public DefaultSettingService(UserRepository userRepository, DefaultSettingRepository defaultSettingRepository) {
        this.userRepository = userRepository;
        this.defaultSettingRepository = defaultSettingRepository;
    }

    public GetProfileRes getUserInformation(GetSettingReq req) {
        User user = userRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        String nickname = user.getNickname();
        LocalDate birthdate = user.getBirthdate();

        return new GetProfileRes(nickname, birthdate);
    }

    @Transactional
    public SetDefaultSettingRes SetDefaultAlarm(SetDefaultSettingReq req) {
        DefaultSetting defaultSetting = defaultSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        defaultSetting.setCanAlarm(req.canAlarm());
        defaultSettingRepository.save(defaultSetting);

        SetDefaultSettingRes setDefaultSettingRes = SetDefaultSettingRes.builder()
                .userId(req.userId())
                .canAlarm(req.canAlarm())
                .build();

        return setDefaultSettingRes;
    }
}
