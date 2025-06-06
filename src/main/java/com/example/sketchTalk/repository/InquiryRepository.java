package com.example.sketchTalk.repository;

import com.example.sketchTalk.model.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Optional<List<Inquiry>> findAllByUserId(Long userId);

    // 답변되지 않은 항목들만 어떻게 표현하지?
}
