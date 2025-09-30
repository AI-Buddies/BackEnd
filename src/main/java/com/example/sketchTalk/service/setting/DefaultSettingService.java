package com.example.sketchTalk.service.setting;

import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DefaultSettingService {
    // TODO: 토큰 구현 후 수정
    private final UserRepository userRepository;

    public DefaultSettingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GetProfileRes getUserInformation(GetSettingReq req) {
        User user = userRepository.findByUserId(req.userId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        String nickname = user.getNickname();
        LocalDate birthdate = user.getBirthdate();

        return new GetProfileRes(nickname, birthdate);
    }
}
