package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.category.out.CategoryCompletionRes;
import com.example.sketchTalk.model.entity.achievement.UserCategory;
import com.example.sketchTalk.model.entity.achievement.UserCategoryKey;
import com.example.sketchTalk.repository.achievement.CategoryRepository;
import com.example.sketchTalk.repository.achievement.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;

    public List<CategoryCompletionRes> updateCategory(long userId, List<Long> categoryIds) {
        List<CategoryCompletionRes> results = CompletedCategoryInfo(userId, categoryIds);
        results.forEach(res -> {
            UserCategoryKey key = new UserCategoryKey(userId, res.categoryId());
            UserCategory userCategory = new UserCategory(key, true, LocalDate.now());
            userCategoryRepository.save(userCategory);
        });
        return results;
    }

    //유저가 클리어한 카테고리를 확인
    private List<CategoryCompletionRes> CompletedCategoryInfo(Long userId, List<Long> subs) {
        List<Object[]> results = categoryRepository.findCompletedCategoryByUserAndList(userId, subs);
        return results.stream()
                .map(row -> new CategoryCompletionRes(
                        (long) row[0],
                        (String) row[1]
                ))
                .collect(Collectors.toList());
    }
}





