package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.user.in.*;
import com.example.sketchTalk.dto.user.out.UpdateUserInfoRes;
import com.example.sketchTalk.dto.user.out.LoginRes;
import com.example.sketchTalk.dto.user.out.RegisterRes;
import com.example.sketchTalk.dto.user.out.UserRes;
import com.example.sketchTalk.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/logout")
    public ApiResponse<UserRes> logout(@AuthenticationPrincipal Long userId) {
        UserRes result = service.logout(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PutMapping
    public ApiResponse<UpdateUserInfoRes> updateUserInformation(@RequestBody UpdateUserInfoReq updateUserInfoReq) {
        UpdateUserInfoRes result = service.updateUserInformation(updateUserInfoReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @DeleteMapping
    public ApiResponse<UserRes> delete(@AuthenticationPrincipal Long userId) {
        UserRes result = service.delete(userId);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}