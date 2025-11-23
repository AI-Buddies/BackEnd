package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.user.in.*;
import com.example.sketchTalk.dto.user.out.*;
import com.example.sketchTalk.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ApiResponse<LoginRes> login(
            @RequestBody LoginReq loginReq,
            HttpServletResponse response
    ) {
        LoginRes result = service.login(loginReq);

        // Access Token 쿠키 설정
        response.addHeader("Set-Cookie",
                "access_token=" + result.accessToken() +
                        "; Path=/; Max-Age=3600; HttpOnly; Secure; SameSite=None");

        // Refresh Token 쿠키 설정
        response.addHeader("Set-Cookie",
                "refresh_token=" + result.refreshToken() +
                        "; Path=/; Max-Age=1209600; HttpOnly; Secure; SameSite=None");

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @GetMapping("/id/availability")
    public ApiResponse<CheckDuplicateIdRes> checkDuplicateId(@RequestBody CheckDuplicateIdReq checkDuplicateIdReq) {
        CheckDuplicateIdRes result = service.checkDuplicateId(checkDuplicateIdReq);

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }

    @PostMapping("/register")
    public ApiResponse<RegisterRes> register(@RequestBody RegisterReq registerReq) {
        RegisterRes result = service.register(registerReq);

        return ApiResponse.onSuccess(HttpStatus.CREATED, result);
    }

    @PostMapping("/logout")
    public ApiResponse<UserRes> logout(
            @AuthenticationPrincipal Long userId,
            @RequestBody LogoutReq logoutReq
    ) {
        UserRes result = service.logout(userId, logoutReq);

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