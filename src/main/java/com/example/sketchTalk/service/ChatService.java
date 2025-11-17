package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.in.SelectedImageReq;
import com.example.sketchTalk.dto.chat.out.CompletedDiaryRes;
import com.example.sketchTalk.dto.chat.out.DiaryRes;
import com.example.sketchTalk.dto.chat.out.ImageRes;
import com.example.sketchTalk.dto.chat.out.ReplyRes;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.dto.webClient.in.ImageDataBody;
import com.example.sketchTalk.dto.webClient.in.SecondImageDataBody;
import com.example.sketchTalk.exception.chat.ChatExceptions;
import com.example.sketchTalk.exception.diary.DiaryExceptions;
import com.example.sketchTalk.model.Style;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.model.entity.Image;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.repository.ImageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AIRequestService aiRequestService;
    private final DiaryRepository diaryRepository;
    private final ImageRepository imageRepository;

    public ChatDataBody getReply(ChatReq chatReq, Long userId) {
        System.out.println("text : " + chatReq.dialog());
        ReplyRes replyRes = aiRequestService.sendChat(userId, chatReq.dialog());
        if(replyRes.isSuccess()) return replyRes.data();
        else throw new CustomException(ChatExceptions.SEND_CHAT_ERROR);
    }

    public DiaryDataBody getDiary(Long userId) {
        DiaryRes diaryRes = aiRequestService.requestDiary(userId);
        if(diaryRes.isSuccess()) return diaryRes.data();
        else throw new CustomException(ChatExceptions.WRITE_DIARY_ERROR);
    }

    public ImageDataBody getImage(DrawReq req, Long userId) {
        ImageRes imageRes = aiRequestService.requestImage(userId, req);
        if(imageRes.isSuccess()) return new ImageDataBody(imageRes.data().diaryId(), req.style(), imageRes.data().imageURL());
        else throw new CustomException(ChatExceptions.DRAW_IMAGE_ERROR);
    }

    public SecondImageDataBody getTwoImages(Style style, ImageDataBody imageDataBody, String prevImageUrl) {
        return new SecondImageDataBody(imageDataBody.diaryId(), imageDataBody.imageURL(), prevImageUrl);
    }

    /*@Transactional
    public CompletedDiaryRes getCompletedDiary(SelectedImageReq req) {
        //일기 저장
        Diary diary = diaryRepository.findByDiaryId(req.diaryId()).orElseThrow(()->new CustomException(DiaryExceptions.DIARY_NOT_FOUND, req.diaryId()));
        Image image = new Image(diary, req.style(), req.imageUrl());
        diary.saveImage(image);
        imageRepository.save(image);
        //코멘트 요청(병렬)

        //도전과제 검색(병렬)
        //응답 생성 및 반환
    }*/
}
