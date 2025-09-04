package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.dto.setting.out.DefaultSettingRes;
import com.example.sketchTalk.model.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultSettingService {
    // TODO: 토큰 구현 후 수정
    User user = null;

    public DefaultSettingRes getUserInformation() {
        // 토큰 구현 후 관련 로직 추가하기

        String nickname = user.getNickname();
        LocalDate birthdate = user.getBirthdate();

        return new DefaultSettingRes(nickname, birthdate);
    }
}
