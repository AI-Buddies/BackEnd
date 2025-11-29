package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.user.in.*;
import com.example.sketchTalk.dto.user.out.*;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.entity.DeviceToken;
import com.example.sketchTalk.model.entity.RefreshToken;
import com.example.sketchTalk.model.entity.User;
import com.example.sketchTalk.repository.DeviceTokenRepository;
import com.example.sketchTalk.repository.UserRepository;

import com.example.sketchTalk.security.jwt.JwtUtils;
import com.example.sketchTalk.service.setting.SettingProvisioningService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final DeviceTokenRepository deviceTokenRepository;

    private final SettingProvisioningService settingProvisioningService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public LoginRes login(LoginReq req) {
        User user = authenticateAndGetUser(req.loginId(),  req.password());

        String accessToken = jwtUtils.generateJwtToken(user.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());

        saveDeviceInfo(user, req.deviceToken(), req.deviceType(), req.deviceIdentifier());

        return new LoginRes(user.getNickname(), accessToken, refreshToken.getToken());
    }

    public CheckDuplicateIdRes checkDuplicateId(String loginId) {
        boolean isAvailable = !userRepository.existsByLoginId(loginId);

        return new CheckDuplicateIdRes(isAvailable);
    }

    @Transactional
    public RegisterRes register(RegisterReq req) {

        // 1. 중복 ID 확인
        userRepository.findByLoginId(req.loginId())
                .ifPresent(user -> {
                    throw new CustomException(UserExceptions.ID_ALREADY_EXISTS);
                });

        User newUser = User.builder()
                .loginId(req.loginId())
                .password(passwordEncoder.encode(req.password()))
                .nickname(req.nickname())
                .birthdate(req.birthdate())
                .build();

        userRepository.save(newUser);

        // 2. 기본 Setting 값 설정
        settingProvisioningService.provisionDefaultSetting(newUser.getUserId());

        // 3. 토큰 발급
        String accessToken = jwtUtils.generateJwtToken(newUser.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(newUser.getUserId());

        // 4. FCM토큰 저장
        saveDeviceInfo(newUser, req.deviceToken(), req.deviceType(), req.deviceIdentifier());

        return new RegisterRes(newUser.getNickname(), accessToken, refreshToken.getToken());
    }

    @Transactional
    public UserRes logout(Long userId, LogoutReq req) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        Optional<DeviceToken> deviceToken = deviceTokenRepository.findByUserIdAndDeviceIdentifier(userId, req.deviceIdentifier());

        refreshTokenService.deleteByUserId(userId);

        deviceToken.ifPresent(token -> {
            token.revoke();
            deviceTokenRepository.save(token);
        });

        return new UserRes(user.getNickname());
    }

    @Transactional
    public UpdateUserInfoRes updateUserInformation(UpdateUserInfoReq req) {
        User user = userRepository.findByLoginId(req.loginId())
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        user.updatePassword(passwordEncoder.encode(req.password()));
        user.updateNickname(req.nickname());
        user.updateBirthdate(req.birthdate());

        userRepository.save(user);

        return new UpdateUserInfoRes(user.getNickname(), user.getBirthdate());
    }

    @Transactional
    public UserRes delete(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        userRepository.delete(user);

        // 관련 설정 삭제
        settingProvisioningService.deleteUserSetting(user.getUserId());

        deviceTokenRepository.deleteAllByUserId(userId);

        return new UserRes(user.getNickname());
    }

    private User authenticateAndGetUser(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(UserExceptions.PASSWORD_MISMATCH);
        }

        return user;
    }

    private void saveDeviceInfo(User user, String deviceToken, String deviceType, String deviceIdentifier) {
        // 토큰은 우선 선택적으로 받는다.
        if (deviceToken == null || deviceToken.isBlank()) {
            return;
        }

        Optional<DeviceToken> prevDeviceToken = deviceTokenRepository.findByUserIdAndDeviceIdentifier(user.getUserId(), deviceIdentifier);

        // 이전 기록이 존재하는 경우
        if (prevDeviceToken.isPresent()) {
            prevDeviceToken.get().updateFcmToken(deviceToken);
            return;
        }

        // 이전 기록이 없는 경우
        DeviceToken newDeviceToken = DeviceToken.builder()
                .userId(user.getUserId())
                .fcmToken(deviceToken)
                .deviceType(deviceType)
                .deviceIdentifier(deviceIdentifier)
                .build();

        deviceTokenRepository.save(newDeviceToken);
    }
}