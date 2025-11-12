package com.example.sketchTalk.repository.achievement;

import com.example.sketchTalk.model.entity.achievement.Category;
import com.example.sketchTalk.model.entity.achievement.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    int countByCategory(Category category);

    @Query("SELECT s.subId FROM SubCategory s WHERE s.category = :category")
    List<Long> findIdsByCategory(Category category);

    List<SubCategory> findByCategory(Category category);
}
