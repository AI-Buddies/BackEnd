package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.user.in.ChangeNicknameReq;
import com.example.sketchTalk.dto.user.in.ChangePasswordReq;
import com.example.sketchTalk.dto.user.in.LoginReq;
import com.example.sketchTalk.dto.user.in.RegisterReq;
import com.example.sketchTalk.dto.user.out.LoginRes;
import com.example.sketchTalk.dto.user.out.RegisterRes;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.exception.user.UserException;
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
        User user = repository.findByLoginId(loginReq.getLoginId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new UserException(UserExceptions.PASSWORD_MISMATCH);
        }

        return user;
    }

    @Transactional
    public LoginRes login(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        String accessToken = jwtUtils.generateJwtToken(user.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUserId());

        return new LoginRes(accessToken, refreshToken.getToken());
    }

    @Transactional
    public RegisterRes register(RegisterReq registerReq) {

        // 1. 중복 ID 확인
        repository.findByLoginId(registerReq.getLoginId())
                .ifPresent(user -> {
                    throw new UserException(UserExceptions.ID_ALREADY_EXISTS);
                });

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        // 2. 생년월일 타당성 확인
        if (registerReq.getBirthdate().isAfter(LocalDate.now())) {
            throw new UserException(UserExceptions.BIRTHDATE_INVALID);
        }

        User newUser = User.builder()
                .loginId(registerReq.getLoginId())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .nickname(registerReq.getNickname())
                .birthdate(registerReq.getBirthdate())
                .build();

        repository.save(newUser);

        // 3. 기본 Setting 값 설정
        settingProvisioningService.provisionDefaultSetting(newUser.getUserId());

        // 4. 토큰 발급
        String accessToken = jwtUtils.generateJwtToken(newUser.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(newUser.getUserId());

        return new RegisterRes(accessToken, refreshToken.getToken());
    }

    // TODO: 로그아웃 추가

    public UserRes changePassword(ChangePasswordReq changePasswordReq) {
        LoginReq loginReq = new LoginReq(changePasswordReq.getLoginId(), changePasswordReq.getOldPassword());

        User currentUser = authenticateAndGetUser(loginReq);

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        currentUser.updatePassword(passwordEncoder.encode(changePasswordReq.getNewPassword()));

        repository.save(currentUser);

        return new UserRes("PASSWORD_CHANGED");
    }

    public UserRes changeNickname(ChangeNicknameReq changeNicknameReq) {
        LoginReq loginReq = new LoginReq(changeNicknameReq.getLoginId(), changeNicknameReq.getPassword());

        User currentUser = authenticateAndGetUser(loginReq);

        currentUser.updateNickname(changeNicknameReq.getNewNickname());

        repository.save(currentUser);

        return new UserRes("NICKNAME_CHANGED");
    }

    @Transactional
    public UserRes delete(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        repository.delete(user);

        // 관련 설정 삭제
        settingProvisioningService.deleteUserSetting(user.getUserId());

        return new UserRes("DELETE_SUCCESS");
    }
}