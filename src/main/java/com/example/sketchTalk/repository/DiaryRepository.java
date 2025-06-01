package com.example.sketchTalk.repository;

import com.example.sketchTalk.model.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
