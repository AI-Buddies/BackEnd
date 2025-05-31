package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.ChangePasswordDTO;
import com.example.sketchTalk.dto.LoginDTO;
import com.example.sketchTalk.model.User;
import com.example.sketchTalk.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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

    private User authenticateAndGetUser(LoginDTO loginDTO) {
        User user = repository.findByLoginId(loginDTO.getLoginId())
                .orElseThrow(() -> new RuntimeException("ID_NOT_FOUND"));


        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
           throw new RuntimeException("PASSWORD_MISMATCH");
        }

        return user;
    }

    public ResponseEntity<String> login(LoginDTO loginDTO) {
        try {
            authenticateAndGetUser(loginDTO);

            return ResponseEntity.ok("LOGIN_SUCCESS");
        }

        catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }


    public ResponseEntity<String> register(User user) {
        Optional<User> userById = repository.findByLoginId(user.getLoginId());

        if (userById.isPresent()) {
            return ResponseEntity.status(409).body("ID_ALREADY_EXISTS");
        }

        // TODO: 비밀번호 제약조건이 필요하다면 이쪽에 넣기!!

        Optional<User> userByNickname = repository.findByNickname(user.getNickname());

        if (userByNickname.isPresent()) {
            return ResponseEntity.status(409).body("NICKNAME_ALREADY_EXISTS");
        }

        if (user.getBirthDate().isAfter(LocalDate.now())) {
            return ResponseEntity.status(400).body("BIRTH_DATE_INVALID");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);

        return ResponseEntity.ok("REGISTER_SUCCESS");
    }

    // TODO: 로그아웃 추가

    public ResponseEntity<String> changePassword(ChangePasswordDTO changePasswordDTO) {
        LoginDTO loginDTO = new LoginDTO(changePasswordDTO.getLoginId(), changePasswordDTO.getOldPassword());

        try {
            User user = authenticateAndGetUser(loginDTO);

            // TODO: 비밀번호 제약조건이 필요하다면 이쪽에 넣기!!

            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            repository.save(user);

            return ResponseEntity.ok("PASSWORD_CHANGED");
        }

        catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    public ResponseEntity<String> delete(LoginDTO loginDTO) {
        try {
            User user = authenticateAndGetUser(loginDTO);

            repository.delete(user);
            return ResponseEntity.ok("DELETE_SUCCESS");
        }

        catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}