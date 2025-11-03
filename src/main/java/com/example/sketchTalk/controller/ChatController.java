package com.example.sketchTalk.controller;

import com.example.sketchTalk._core.common.ApiResponse;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ApiResponse<ChatDataBody> chat(@RequestBody ChatReq req) {
        ChatDataBody res = chatService.getReply(req);
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }

    @PostMapping("/diary")
    public ApiResponse<DiaryDataBody> writeDiary() {
        DiaryDataBody res = chatService.getDiary();
        return ApiResponse.onSuccess(HttpStatus.CREATED, res);
    }
}
