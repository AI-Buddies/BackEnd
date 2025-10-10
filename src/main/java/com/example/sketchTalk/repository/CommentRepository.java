package com.example.sketchTalk.repository;

import com.example.sketchTalk.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
