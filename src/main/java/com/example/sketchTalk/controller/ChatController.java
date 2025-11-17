package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.in.PrevDrawReq;
import com.example.sketchTalk.dto.chat.in.SelectedImageReq;
import com.example.sketchTalk.dto.chat.out.CompletedDiaryRes;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.dto.webClient.in.ImageDataBody;
import com.example.sketchTalk.dto.webClient.in.SecondImageDataBody;
import com.example.sketchTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("")
    public ApiResponse<ChatDataBody> chat(@RequestBody ChatReq req, @AuthenticationPrincipal Long userId) {
        ChatDataBody res = chatService.getReply(req, userId);
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }

    @PostMapping("/diary")
    public ApiResponse<DiaryDataBody> writeDiary(@AuthenticationPrincipal Long userId) {
        DiaryDataBody res = chatService.getDiary(userId);
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }

    @PostMapping("/image")
    public ApiResponse<ImageDataBody> drawImage(@RequestBody DrawReq req, @AuthenticationPrincipal Long userId) {
        ImageDataBody res = chatService.getImage(req, userId);
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }

    @PostMapping("/image/retry")
    public ApiResponse<SecondImageDataBody> retryDrawImage(@RequestBody PrevDrawReq req, @AuthenticationPrincipal Long userId) {
        DrawReq drawReq = new DrawReq(req.content(), req.style());
        ImageDataBody newImageRes = chatService.getImage(drawReq, userId);
        SecondImageDataBody secondImageRes = chatService.getTwoImages(req.style(), newImageRes, req.prevImageUrl());
        return ApiResponse.onSuccess(HttpStatus.CREATED, secondImageRes);
    }

    /*@PostMapping("/image/save")
    public ApiResponse<CompletedDiaryRes> completeDiary(@RequestBody SelectedImageReq req) {
        CompletedDiaryRes res = chatService.getCompletedDiary(req);
    }*/
}
