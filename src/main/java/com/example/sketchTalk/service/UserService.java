package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.ResponseDTO;
import com.example.sketchTalk.dto.in.ChangeNicknameDTO;
import com.example.sketchTalk.dto.in.ChangePasswordDTO;
import com.example.sketchTalk.dto.in.LoginDTO;
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

    public User authenticateAndGetUser(LoginDTO loginDTO) {
        User user = repository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow( () -> new RuntimeException("ID_NOT_FOUND"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("PASSWORD_MISMATCH");
        }

        return user;
    }

    public ResponseDTO login(LoginDTO loginDTO) {
        try {
            User user = authenticateAndGetUser(loginDTO);

            return new ResponseDTO(true, "LOGIN_SUCCESS");
        } catch (RuntimeException e) {
            return new ResponseDTO (false, e.getMessage());
        }
    }

    public ResponseDTO register(User user) {
        Optional<User> userOpt = repository.findByLoginId(user.getLoginId());

        if (userOpt.isPresent()) {
            return new ResponseDTO(false,"ID_ALREADY_EXISTS");
        }

        // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

        if (existNickname(user.getNickname())) {
            return new ResponseDTO(false, "NICKNAME_ALREADY_EXISTS");
        }

        if (user.getBirthdate().isAfter(LocalDate.now())) {
            return new ResponseDTO(false, "BIRTH_DATE_INVALID");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);

        return new ResponseDTO(true, "REGISTER_SUCCESS");
    }

    // TODO: 로그아웃 추가

    public ResponseDTO changePassword(ChangePasswordDTO changePasswordDTO) {
        LoginDTO loginDTO = new LoginDTO(changePasswordDTO.getLoginId(), changePasswordDTO.getOldPassword());

        try {
            User user = authenticateAndGetUser(loginDTO);

            // TODO: 비밀번호 제약조건이 필요하다면 이곳에 넣기!!

            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            repository.save(user);

            return new ResponseDTO(true, "PASSWORD_CHANGED");
        } catch (RuntimeException e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

    public ResponseDTO changeNickname(ChangeNicknameDTO changeNicknameDTO) {
        LoginDTO loginDTO = new LoginDTO(changeNicknameDTO.getLoginId(), changeNicknameDTO.getPassword());

        try {
            User user = authenticateAndGetUser(loginDTO);

            if (existNickname(changeNicknameDTO.getNewNickname())) {
                return new ResponseDTO(false, "NICKNAME_ALREADY_EXISTS");
            }

            user.setNickname(changeNicknameDTO.getNewNickname());
            repository.save(user);
            return new ResponseDTO(true, "NICKNAME_CHANGED");
        } catch (RuntimeException e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }

    public ResponseDTO delete(LoginDTO loginDTO) {
        try {
            User user = authenticateAndGetUser(loginDTO);

            repository.delete(user);
            return new ResponseDTO(true, "DELETE_SUCCESS");
        } catch(RuntimeException e) {
            return new ResponseDTO(false, e.getMessage());
        }
    }
}