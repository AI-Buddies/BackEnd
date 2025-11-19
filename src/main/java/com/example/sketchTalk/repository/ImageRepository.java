package com.example.sketchTalk.repository;

import com.example.sketchTalk.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
