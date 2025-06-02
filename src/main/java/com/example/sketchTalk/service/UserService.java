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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existNickname(String nickname) {
        Optional<User> userOpt = repository.findByNickname(nickname);

        return userOpt.isPresent();
    }

    public User authenticateAndGetUser(LoginReq loginReq) {
        User user = repository.findByLoginId(loginReq.getLoginId())
                .orElseThrow( () -> new UserException(UserExceptions.ID_NOT_FOUND));

        if (!passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            throw new UserException(UserExceptions.PASSWORD_MISMATCH);
        }

        return user;
    }

    public UserRes login(LoginReq loginReq) {

        // 반환받지 않고 authenticate
        authenticateAndGetUser(loginReq);

        return new UserRes("LOGIN_SUCCESS");
    }

    public UserRes register(RegisterReq registerReq) {

        // 1. 중복 ID 확인
        repository.findByLoginId(registerReq.getLoginId())
                .ifPresent(user -> {
                    throw new UserException(UserExceptions.ID_ALREADY_EXISTS);
                });

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!


        // 2. 중복 닉네임 확인
        if (existNickname(registerReq.getNickname())) {
            throw new UserException(UserExceptions.NICKNAME_ALREADY_EXISTS);
        }

        // 3. 생년월일 타당성 확인
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

        return new UserRes("REGISTER_SUCCESS");
    }

    // TODO: 로그아웃 추가

    public UserRes changePassword(ChangePasswordReq changePasswordReq) {
        LoginReq loginReq = new LoginReq(changePasswordReq.getLoginId(), changePasswordReq.getOldPassword());

        User currentUser = authenticateAndGetUser(loginReq);

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        User newUser = User.builder()
                .loginId(currentUser.getLoginId())
                .password(passwordEncoder.encode(changePasswordReq.getNewPassword()))
                .nickname(currentUser.getNickname())
                .birthdate(currentUser.getBirthdate())
                .build();

        repository.save(newUser);

        return new UserRes("PASSWORD_CHANGED");
    }

    public UserRes changeNickname(ChangeNicknameReq changeNicknameReq) {
        LoginReq loginReq = new LoginReq(changeNicknameReq.getLoginId(), changeNicknameReq.getPassword());

        User currentUser = authenticateAndGetUser(loginReq);

        if (existNickname(changeNicknameReq.getNewNickname())) {
            throw new UserException(UserExceptions.NICKNAME_ALREADY_EXISTS);
        }

        User newUser = User.builder()
                .loginId(currentUser.getLoginId())
                .password(currentUser.getPassword())
                .nickname(changeNicknameReq.getNewNickname())
                .birthdate(currentUser.getBirthdate())
                .build();

        repository.save(newUser);

        return new UserRes("NICKNAME_CHANGED");
    }

    public UserRes delete(LoginReq loginReq) {
        User user = authenticateAndGetUser(loginReq);

        repository.delete(user);

        return new UserRes("DELETE_SUCCESS");
    }
}