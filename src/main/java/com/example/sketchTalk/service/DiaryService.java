package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.comment.out.ReqContentRes;
import com.example.sketchTalk.dto.comment.out.SaveCommentRes;
import com.example.sketchTalk.dto.diary.in.ModifyDiaryReq;
import com.example.sketchTalk.dto.diary.in.SaveDiaryReq;
import com.example.sketchTalk.dto.diary.out.ModifyDiaryRes;
import com.example.sketchTalk.dto.diary.out.SaveDiaryRes;
import com.example.sketchTalk.exception.diary.DiaryNotFoundException;
import com.example.sketchTalk.model.entity.Comment;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.DiaryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final CommentService commentService;

    public SaveDiaryRes putDiary(SaveDiaryReq saveDiaryReq) {
        Diary diary = new Diary(saveDiaryReq);
        Diary savedDiary = diaryRepository.save(diary);

        ReqContentRes writtenComment = commentService.reqComment(diary.getContent());
        SaveCommentReq saveCommentReq = new SaveCommentReq(savedDiary.getDiaryId(), writtenComment.content());
        SaveCommentRes saveCommentRes = commentService.putComment(saveCommentReq);

        return new SaveDiaryRes(savedDiary.getDiaryId(), saveCommentRes.commentId());
    }

    @Transactional
    public ModifyDiaryRes changeDiary(ModifyDiaryReq modifyDiaryReq) {
        //다이어리 찾기
        Diary diary = diaryRepository.findById(modifyDiaryReq.diaryId()).orElseThrow(()->new DiaryNotFoundException(modifyDiaryReq.diaryId()));
        diary.rewriteDiary(modifyDiaryReq.title(), modifyDiaryReq.content(), modifyDiaryReq.emotion());
        diaryRepository.save(diary);

        ModifyDiaryRes modifyDiaryRes = ModifyDiaryRes.builder()
                .diaryId(diary.getDiaryId())
                .userId(diary.getUserId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .date(diary.getDate())
                .emotion(diary.getEmotion())
                .build();
        return modifyDiaryRes;
    }

    /*
    단어 목록을 가져와서 HastSet을 만들고
    문자열 내 검색, 존재하는 단어 탐색하여 배열로 반환
    UserSub에 추가를 하고
    전체 카테고리를 달성헀는지 확인
     */
    private void checkAchievement(String s) {
        //여기서 각 함수를 호출해서 실행
    }

    private List<String> getCategoryNames() {
        //단어 목록 가져오기
        return null;
    }

    private List<Long> findSubs() {
        //문자열 내 검색
        return null;
    }

    private void updateCategory(List<Long> subs) {

    }

    private void updateAchievement() {

    }
}
