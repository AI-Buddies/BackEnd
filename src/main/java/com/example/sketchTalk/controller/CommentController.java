package com.example.sketchTalk.controller;

import com.example.sketchTalk.dto.comment.in.SaveCommentReq;
import com.example.sketchTalk.dto.comment.out.SaveCommentRes;
import com.example.sketchTalk.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity<SaveCommentRes> saveComment(@RequestBody SaveCommentReq req) {
        SaveCommentRes saveCommentRes = commentService.putComment(req);
        return ResponseEntity.ok(saveCommentRes);
    }

}
