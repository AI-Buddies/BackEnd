package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.out.DiaryRes;
import com.example.sketchTalk.dto.chat.out.ImageRes;
import com.example.sketchTalk.dto.chat.out.ReplyRes;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.dto.webClient.in.ImageDataBody;
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

    public DiaryDataBody getDiary() {
        long userId = 1L;
        DiaryRes diaryRes = aiRequestService.requestDiary(userId);
        if(diaryRes.isSuccess()) return diaryRes.data();
        else throw new CustomException(ChatExceptions.WRITE_DIARY_ERROR);
    }

    public ImageDataBody getImage(DrawReq req) {
        long userId = 1L;
        ImageRes imageRes = aiRequestService.requestImage(userId, req.content());
        if(imageRes.isSuccess()) return imageRes.data();
        else throw new CustomException(ChatExceptions.DRAW_IMAGE_ERROR);
    }
}
