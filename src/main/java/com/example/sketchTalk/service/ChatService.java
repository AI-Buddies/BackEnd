package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.out.ReplyRes;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.exception.chat.ChatExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AIRequestService aiRequestService;

    public ChatDataBody getReply(ChatReq chatReq) {
        long userId = 1L;
        System.out.println("text : " + chatReq.dialog());
        ReplyRes replyRes = aiRequestService.sendChat(userId, chatReq.dialog());
        if(replyRes.isSuccess()) return replyRes.data();
        else throw new CustomException(ChatExceptions.SEND_CHAT_ERROR);
    }
}
