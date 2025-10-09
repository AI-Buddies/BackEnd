package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.refreshToken.RefreshReq;
import com.example.sketchTalk.dto.refreshToken.RefreshRes;
import com.example.sketchTalk.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshRes>> reIssueAccessToken(@RequestBody RefreshReq refreshReq) {
        RefreshRes result = refreshTokenService.reissueAccessToken(refreshReq);

        ApiResponse<RefreshRes> body = ApiResponse.onSuccess(HttpStatus.OK, result);

        return ResponseEntity
                .status(body.getHttpStatus())
                .body(body);
    }
}