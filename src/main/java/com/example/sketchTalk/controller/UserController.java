package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.user.in.ChangeNicknameReq;
import com.example.sketchTalk.dto.user.in.ChangePasswordReq;
import com.example.sketchTalk.dto.user.in.LoginReq;
import com.example.sketchTalk.dto.user.in.RegisterReq;
import com.example.sketchTalk.dto.user.out.LoginRes;
import com.example.sketchTalk.dto.user.out.RegisterRes;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ApiResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
        LoginRes result = service.login(loginReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PostMapping("/register")
    public ApiResponse<RegisterRes> register(@RequestBody RegisterReq registerReq) {
        RegisterRes result = service.register(registerReq);

        return ApiResponse.onSuccess(HttpStatus.CREATED, result);
    }

    // TODO: 로그아웃 추가

    @PatchMapping("/password")
    public ApiResponse<UserRes> changePassword(@RequestBody ChangePasswordReq changePasswordReq) {
        UserRes result = service.changePassword(changePasswordReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PatchMapping("/nickname")
    public ApiResponse<UserRes> changeNickname(@RequestBody ChangeNicknameReq changeNicknameReq) {
        UserRes result = service.changeNickname(changeNicknameReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @DeleteMapping
    public ApiResponse<UserRes> delete(@RequestBody LoginReq loginReq) {
        UserRes result = service.delete(loginReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}