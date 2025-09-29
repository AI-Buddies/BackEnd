package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.comment.out.SaveCommentRes;
import com.example.sketchTalk.model.entity.Comment;
import com.example.sketchTalk.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public SaveCommentRes putComment(SaveCommentReq saveCommentReq) {
        Comment comment = new Comment(saveCommentReq.diaryId(), saveCommentReq.content());
        Comment savedComment = commentRepository.save(comment);
        return new SaveCommentRes(savedComment.getCommentId(), savedComment.getContent());
    }
}
