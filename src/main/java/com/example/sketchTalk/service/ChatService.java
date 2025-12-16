package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.category.out.AchievedResultRes;
import com.example.sketchTalk.dto.chat.in.ChatReq;
import com.example.sketchTalk.dto.chat.in.DrawReq;
import com.example.sketchTalk.dto.chat.in.SelectedImageReq;
import com.example.sketchTalk.dto.chat.out.*;
import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.webClient.in.ChatDataBody;
import com.example.sketchTalk.dto.webClient.in.DiaryDataBody;
import com.example.sketchTalk.dto.webClient.in.ImageDataBody;
import com.example.sketchTalk.dto.webClient.in.SecondImageDataBody;
import com.example.sketchTalk.exception.chat.ChatExceptions;
import com.example.sketchTalk.exception.diary.DiaryExceptions;
import com.example.sketchTalk.exception.user.UserExceptions;
import com.example.sketchTalk.model.Style;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.model.entity.Image;
import com.example.sketchTalk.model.entity.setting.AudioSetting;
import com.example.sketchTalk.repository.DiaryRepository;
import com.example.sketchTalk.repository.ImageRepository;
import com.example.sketchTalk.repository.setting.AudioSettingRepository;
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
    private final CommentService commentService;
    private final DiaryService diaryService;
    private final AudioSettingRepository audioSettingRepository;

    public ChatReplyRes getReply(ChatReq chatReq, Long userId) {
        System.out.println("text : " + chatReq.dialog());
        ReplyRes replyRes = aiRequestService.sendChat(userId, chatReq.dialog());
        if(replyRes.isSuccess()) {
            AudioSetting audioSetting = audioSettingRepository.findByUserId(userId)
                    .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));
            return new ChatReplyRes(replyRes.data().reply(), replyRes.data().isSufficient(), audioSetting.getVoiceType().getValue());
        }
        else throw new CustomException(ChatExceptions.SEND_CHAT_ERROR);
    }

    public WriteDiaryRes getDiary(Long userId) {
        DiaryRes diaryRes = aiRequestService.requestDiary(userId);
        if(diaryRes.isSuccess()) return new WriteDiaryRes(diaryRes.data().title(), diaryRes.data().content(), diaryRes.data().emotion());
        else throw new CustomException(ChatExceptions.WRITE_DIARY_ERROR);
    }

    public DrawImageRes getImage(DrawReq req, Long userId) {
        //줄바꿈 제거
        String requestContent = removeLine(req.content());
        //번역
        DrawReq englishDrawReq = aiRequestService.requestEnglish(userId, requestContent, req);
        System.out.println("englishText : " + englishDrawReq.content());
        ImageRes imageRes = aiRequestService.requestImage(userId, englishDrawReq);
        if(imageRes.isSuccess()) return new DrawImageRes(req.diaryId(), req.style(), imageRes.data().image_url());
        else throw new CustomException(ChatExceptions.DRAW_IMAGE_ERROR);
    }

    private String removeLine(String text) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("\n", "");
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
        //코멘트 요청 및 저장(병렬)
        CommentRes commentRes = aiRequestService.requestComment(userId, diary.getContent());
        SaveCommentReq saveCommentReq = new SaveCommentReq(diary.getDiaryId(), commentRes.data().comment());
        commentService.putComment(saveCommentReq);
        //도전과제 검색(병렬)
        AchievedResultRes achievedResult =  diaryService.checkAchievement(userId, diary.getContent());

        AudioSetting audioSetting = audioSettingRepository.findByUserId(userId)
                .orElseThrow( () -> new CustomException(UserExceptions.ID_NOT_FOUND));


        //응답 생성 및 반환
        CompletedDiaryRes completedDiaryRes = new CompletedDiaryRes(
                diary.getDiaryId(), diary.getDate(), diary.getEmotion(), diary.getTitle(), diary.getContent(),
                image.getUrl(), commentRes.data().comment(), false, achievedResult, audioSetting.getVoiceType().getValue()
        );
        return completedDiaryRes;
    }
}
