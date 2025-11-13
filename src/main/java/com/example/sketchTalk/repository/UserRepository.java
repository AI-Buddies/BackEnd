package com.example.sketchTalk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sketchTalk.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}