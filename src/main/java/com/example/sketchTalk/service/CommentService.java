package com.example.sketchTalk.service;

import com.example.sketchTalk._core.error.CustomException;
import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.comment.out.ReqContentRes;
import com.example.sketchTalk.dto.comment.out.SaveCommentRes;
import com.example.sketchTalk.exception.diary.DiaryExceptions;
import com.example.sketchTalk.model.entity.Comment;
import com.example.sketchTalk.model.entity.Diary;
import com.example.sketchTalk.repository.CommentRepository;
import com.example.sketchTalk.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final DiaryRepository diaryRepository;

    public SaveCommentRes putComment(SaveCommentReq saveCommentReq) {
        Diary diary = diaryRepository.findById(saveCommentReq.diaryId()).orElseThrow(() -> new CustomException(DiaryExceptions.DIARY_NOT_FOUND));

        Comment comment = new Comment(diary, saveCommentReq.content());
        Comment savedComment = commentRepository.save(comment);
        return new SaveCommentRes(savedComment.getCommentId(), savedComment.getContent());
    }

    public ReqContentRes reqComment(String diaryContent) {
        return new ReqContentRes("good!");
    }
}
