package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.ResponseDTO;
import com.example.sketchTalk.dto.user.in.ChangeNicknameReq;
import com.example.sketchTalk.dto.user.in.ChangePasswordReq;
import com.example.sketchTalk.dto.user.in.LoginReq;
import com.example.sketchTalk.dto.user.in.RegisterReq;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<UserRes> login(@RequestBody LoginReq loginReq) {
        UserRes result = service.login(loginReq);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<UserRes> register(@RequestBody RegisterReq registerReq) {
        UserRes result = service.register(registerReq);
        return ResponseEntity.ok(result);
    }

    // TODO: 로그아웃 추가

    @PatchMapping("/password")
    public ResponseEntity<UserRes> changePassword(@RequestBody ChangePasswordReq changePasswordReq) {
        UserRes result = service.changePassword(changePasswordReq);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<UserRes> changeNickname(@RequestBody ChangeNicknameReq changeNicknameReq) {
        UserRes result = service.changeNickname(changeNicknameReq);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<UserRes> delete(@RequestBody LoginReq loginReq) {
        UserRes result = service.delete(loginReq);
        return ResponseEntity.ok(result);
    }
}