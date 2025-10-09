package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.user.in.ChangeNicknameReq;
import com.example.sketchTalk.dto.user.in.ChangePasswordReq;
import com.example.sketchTalk.dto.user.in.LoginReq;
import com.example.sketchTalk.dto.user.in.RegisterReq;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.exception.user.UserException;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.repository.UserRepository;

import com.example.sketchTalk.service.setting.SettingProvisioningService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final SettingProvisioningService settingProvisioningService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, SettingProvisioningService settingProvisioningService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.settingProvisioningService = settingProvisioningService;
    }

    public User authenticateAndGetUser(LoginReq loginReq) {
        User user = repository.findByLoginId(loginReq.loginId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        if (!passwordEncoder.matches(loginReq.password(), user.getPassword())) {
            throw new UserException(UserExceptions.PASSWORD_MISMATCH);
        }

        return user;
    }

    public UserRes login(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        return new UserRes(user.getNickname());
    }

    @Transactional
    public UserRes register(RegisterReq registerReq) {

        // 1. 중복 ID 확인
        repository.findByLoginId(registerReq.loginId())
                .ifPresent(user -> {
                    throw new UserException(UserExceptions.ID_ALREADY_EXISTS);
                });

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        // 2. 생년월일 타당성 확인
        if (registerReq.birthdate().isAfter(LocalDate.now())) {
            throw new UserException(UserExceptions.BIRTHDATE_INVALID);
        }

        User newUser = User.builder()
                .loginId(registerReq.loginId())
                .password(passwordEncoder.encode(registerReq.password()))
                .nickname(registerReq.nickname())
                .birthdate(registerReq.birthdate())
                .build();

        repository.save(newUser);

        // 3. 기본 Setting 값 설정
        settingProvisioningService.provisionDefaultSetting(newUser.getUserId());

        return new UserRes(newUser.getNickname());
    }

    // TODO: 로그아웃 추가

    public UserRes changePassword(ChangePasswordReq changePasswordReq) {
        LoginReq loginReq = new LoginReq(changePasswordReq.loginId(), changePasswordReq.oldPassword());

        User user = authenticateAndGetUser(loginReq);

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        user.updatePassword(passwordEncoder.encode(changePasswordReq.newPassword()));

        repository.save(user);

        return new UserRes(user.getNickname());
    }

    public UserRes changeNickname(ChangeNicknameReq changeNicknameReq) {
        LoginReq loginReq = new LoginReq(changeNicknameReq.loginId(), changeNicknameReq.password());

        User user = authenticateAndGetUser(loginReq);

        user.updateNickname(changeNicknameReq.newNickname());

        repository.save(user);

        return new UserRes(user.getNickname());
    }

    @Transactional
    public UserRes delete(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        repository.delete(user);

        // 관련 설정 삭제
        settingProvisioningService.deleteUserSetting(user.getUserId());

        return new UserRes(user.getNickname());
    }
}