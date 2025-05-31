package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.ChangePasswordDTO;
import com.example.sketchTalk.dto.LoginDTO;
import com.example.sketchTalk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sketchTalk.model.User;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return service.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return service.register(user);
    }

    // TODO: 로그아웃 추가

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return service.changePassword(changePasswordDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody LoginDTO loginDTO) {
        return service.delete(loginDTO);
    }
}
