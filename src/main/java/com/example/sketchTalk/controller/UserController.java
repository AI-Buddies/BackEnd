package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.ResponseDTO;
import com.example.sketchTalk.dto.in.ChangePasswordDTO;
import com.example.sketchTalk.dto.in.LoginDTO;
import com.example.sketchTalk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sketchTalk.model.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        ResponseDTO result = service.login(loginDTO);

        if (!result.isSuccess) {
            return ResponseEntity.status(401).body(result.getMessage());
        }

        return ResponseEntity.ok("LOGIN_SUCCESS");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        ResponseDTO result = service.register(user);

        if (!result.isSuccess) {
            if (result.getMessage().equals("BIRTH_DATE_INVALID"))
                return ResponseEntity.status(400).body(result.getMessage());

            return ResponseEntity.status(409).body(result.getMessage());
        }

        return ResponseEntity.ok("REGISTER_SUCCESS");
    }

    // TODO: 로그아웃 추가

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        ResponseDTO result = service.changePassword(changePasswordDTO);

        if (!result.isSuccess) {
            return ResponseEntity.status(401).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody LoginDTO loginDTO) {
        ResponseDTO result = service.delete(loginDTO);

        if (!result.isSuccess) {
            return ResponseEntity.status(401).body(result.getMessage());
        }

        return ResponseEntity.ok(result.getMessage());
    }
}