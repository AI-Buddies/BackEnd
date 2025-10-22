package com.example.sketchTalk.service.setting;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.setting.in.GetSettingReq;
import com.example.sketchTalk.dto.setting.in.SetDefaultSettingReq;
import com.example.sketchTalk.dto.setting.out.GetAppInfoRes;
import com.example.sketchTalk.dto.setting.out.GetFAQListRes;
import com.example.sketchTalk.dto.setting.out.GetProfileRes;
import com.example.sketchTalk.dto.setting.out.SetDefaultSettingRes;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.model.entity.setting.DefaultSetting;
import com.example.sketchTalk.model.entity.setting.FrequentlyAskedQuestion;
import com.example.sketchTalk.repository.UserRepository;
import com.example.sketchTalk.repository.setting.DefaultSettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

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
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        DefaultSetting defaultSetting = defaultSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        String nickname = user.getNickname();
        LocalDate birthdate = user.getBirthdate();
        boolean canAlarm = defaultSetting.isCanAlarm();

        return new GetProfileRes(nickname, birthdate, canAlarm);
    }

    @Transactional
    public SetDefaultSettingRes SetDefaultAlarm(SetDefaultSettingReq req) {
        DefaultSetting defaultSetting = defaultSettingRepository.findByUserId(req.userId())
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        defaultSetting.setCanAlarm(req.canAlarm());
        defaultSettingRepository.save(defaultSetting);

        SetDefaultSettingRes setDefaultSettingRes = SetDefaultSettingRes.builder()
                .userId(req.userId())
                .canAlarm(req.canAlarm())
                .build();

        return setDefaultSettingRes;
    }

    public GetFAQListRes getFAQList() {
        List<FrequentlyAskedQuestion> list = new ArrayList<>();

        FrequentlyAskedQuestion faq1 = new FrequentlyAskedQuestion(
                "회원가입은 무료인가요?",
                "네, SketchTalk의 모든 기본 기능은 무료입니다."
        );

        FrequentlyAskedQuestion faq2 = new FrequentlyAskedQuestion(
                "사람이 그려주고, 대답해 주는건가요?",
                "아니요, AI가 자동으로 그려주고 대답합니다. 하지만 사람보다 더 예쁘게요!"
        );

        FrequentlyAskedQuestion faq3 = new FrequentlyAskedQuestion(
                "그림이 너무 늦게 완성돼요.",
                "미안해요. 저는 지금도 열심히 색을 섞고 있어요. 조금만 기다려주세요."
        );

        list.add(faq1);
        list.add(faq2);
        list.add(faq3);

        return new GetFAQListRes(list);
    }

    public GetAppInfoRes getAppInformation() {
        return new GetAppInfoRes(
                "Android",
                "1.0.0",
                LocalDate.of(2025, Month.DECEMBER, 19)
        );
    }
}