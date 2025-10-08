package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.refreshToken.RefreshReq;
import com.example.sketchTalk.dto.refreshToken.RefreshRes;
import com.example.sketchTalk.service.RefreshTokenService;
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
    public ResponseEntity<RefreshRes> reIssueAccessToken(@RequestBody RefreshReq refreshReq) {
        RefreshRes refreshTokenRes = refreshTokenService.reissueAccessToken(refreshReq);

        return ResponseEntity.ok(refreshTokenRes);
    }
}