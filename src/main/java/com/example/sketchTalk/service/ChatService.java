package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.in.SelectedImageReq;
import com.example.sketchTalk.dto.chat.out.*;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final AIRequestService aiRequestService;
    private final DiaryRepository diaryRepository;
    private final ImageRepository imageRepository;

    public ChatReplyRes getReply(ChatReq chatReq, Long userId) {
        System.out.println("text : " + chatReq.dialog());
        ReplyRes replyRes = aiRequestService.sendChat(userId, chatReq.dialog());
        if(replyRes.isSuccess()) {
            return new ChatReplyRes(replyRes.data().reply(), replyRes.data().isSufficient(), "boy");//voice 조회 로직 추가예정
        }
        else throw new CustomException(ChatExceptions.SEND_CHAT_ERROR);
    }

    public WriteDiaryRes getDiary(Long userId) {
        DiaryRes diaryRes = aiRequestService.requestDiary(userId);
        if(diaryRes.isSuccess()) return new WriteDiaryRes(diaryRes.data().title(), diaryRes.data().content(), diaryRes.data().emotion());
        else throw new CustomException(ChatExceptions.WRITE_DIARY_ERROR);
    }

    public DrawImageRes getImage(DrawReq req, Long userId) {
        ImageRes imageRes = aiRequestService.requestImage(userId, req);
        if(imageRes.isSuccess()) return new DrawImageRes(req.diaryId(), req.style(), imageRes.data().image_url());
        else throw new CustomException(ChatExceptions.DRAW_IMAGE_ERROR);
    }

    public SecondDrawImageRes getTwoImages(Long diaryId, Style style, String newImageURL, String prevImageUrl) {
        return new SecondDrawImageRes(diaryId, style, newImageURL, prevImageUrl);
    }

    @Transactional
    public CompletedDiaryRes getCompletedDiary(SelectedImageReq req, Long userId) {
        //일기, 그림 저장
        Diary diary = diaryRepository.findByDiaryId(req.diaryId()).orElseThrow(()->new CustomException(DiaryExceptions.DIARY_NOT_FOUND, req.diaryId()));
        Image image = new Image(diary, req.style(), req.imageUrl());
        diary.saveImage(image);
        imageRepository.save(image);
        //코멘트 요청(병렬)
        CommentRes commentRes = aiRequestService.requestComment(userId, diary.getContent());
        //도전과제 검색(병렬)

        //응답 생성 및 반환
        CompletedDiaryRes completedDiaryRes = new CompletedDiaryRes(
                diary.getDiaryId(), diary.getDate(), diary.getEmotion(), diary.getTitle(), diary.getContent(),
                image.getUrl(), commentRes.data().comment(), false, List.of(), "boy"
        );
        return completedDiaryRes;
    }
}
