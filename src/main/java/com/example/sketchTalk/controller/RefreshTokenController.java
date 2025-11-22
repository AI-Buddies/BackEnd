package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.refreshToken.RefreshRes;
import com.example.sketchTalk.exception.token.RtExceptions;
import com.example.sketchTalk.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    public ApiResponse<RefreshRes> reIssueAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
            ) {
        String refreshToken = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            throw new CustomException(RtExceptions.INVALID_TOKEN);
        }

        RefreshRes result = refreshTokenService.reissueAccessToken(refreshToken);

        response.addHeader("Set-Cookie",
                "access_token=" + result.accessToken() +
                        "; Path=/; Max-Age=3600; HttpOnly; Secure; SameSite=None");

        response.addHeader("Set-Cookie",
                "refresh_token=" + result.refreshToken() +
                        "; Path=/; Max-Age=1209600; HttpOnly; Secure; SameSite=None");

        return ApiResponse.onSuccess(HttpStatus.OK, result);
    }
}