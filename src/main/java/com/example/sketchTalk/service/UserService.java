package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.user.in.*;
import com.example.sketchTalk.dto.user.out.UpdateUserInfoRes;
import com.example.sketchTalk.dto.user.out.LoginRes;
import com.example.sketchTalk.dto.user.out.RegisterRes;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.RefreshToken;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.repository.UserRepository;

import com.example.sketchTalk.security.jwt.JwtUtils;
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
    private final RefreshTokenService refreshTokenService;

    private final JwtUtils jwtUtils;

    public UserService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            SettingProvisioningService settingProvisioningService,
            RefreshTokenService refreshTokenService,
            JwtUtils jwtUtils) {

        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.settingProvisioningService = settingProvisioningService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
    }

    public User authenticateAndGetUser(LoginReq loginReq) {
        User user = repository.findByLoginId(loginReq.loginId())
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        if (!passwordEncoder.matches(loginReq.password(), user.getPassword())) {
            throw new CustomException(UserExceptions.PASSWORD_MISMATCH);
        }

        return user;
    }

    @Transactional
    public LoginRes login(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        String accessToken = jwtUtils.generateJwtToken(user.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());

        return new LoginRes(user.getNickname(), accessToken, refreshToken.getToken());
    }

    @Transactional
    public RegisterRes register(RegisterReq registerReq) {

        // 1. 중복 ID 확인
        repository.findByLoginId(registerReq.loginId())
                .ifPresent(user -> {
                    throw new CustomException(UserExceptions.ID_ALREADY_EXISTS);
                });

        User newUser = User.builder()
                .loginId(registerReq.loginId())
                .password(passwordEncoder.encode(registerReq.password()))
                .nickname(registerReq.nickname())
                .birthdate(registerReq.birthdate())
                .build();

        repository.save(newUser);

        // 2. 기본 Setting 값 설정
        settingProvisioningService.provisionDefaultSetting(newUser.getUserId());

        // 3. 토큰 발급
        String accessToken = jwtUtils.generateJwtToken(newUser.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(newUser.getUserId());

        return new RegisterRes(newUser.getNickname(), accessToken, refreshToken.getToken());
    }

    public UserRes logout(Long userId) {
        User user = repository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        refreshTokenService.deleteByUserId(userId);

        return new UserRes(user.getNickname());
    }

    @Transactional
    public UpdateUserInfoRes updateUserInformation(UpdateUserInfoReq req) {
        User user = repository.findByLoginId(req.loginId())
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        user.updatePassword(passwordEncoder.encode(req.password()));
        user.updateNickname(req.nickname());
        user.updateBirthdate(req.birthdate());

        repository.save(user);

        return new UpdateUserInfoRes(user.getNickname(), user.getBirthdate());
    }

    @Transactional
    public UserRes delete(Long userId) {
        User user = repository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        repository.delete(user);

        // 관련 설정 삭제
        settingProvisioningService.deleteUserSetting(user.getUserId());

        return new UserRes(user.getNickname());
    }
}