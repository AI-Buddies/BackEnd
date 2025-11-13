package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.category.out.CategoryCompletionRes;
import com.example.sketchTalk.dto.category.out.CategoryDetailRes;
import com.example.sketchTalk.dto.category.out.CategoryListRes;
import com.example.sketchTalk.dto.category.out.SubCategoryRes;
import com.example.sketchTalk.model.entity.achievement.Category;
import com.example.sketchTalk.model.entity.achievement.SubCategory;
import com.example.sketchTalk.model.entity.achievement.UserCategory;
import com.example.sketchTalk.model.entity.achievement.UserCategoryKey;
import com.example.sketchTalk.repository.achievement.CategoryRepository;
import com.example.sketchTalk.repository.achievement.SubCategoryRepository;
import com.example.sketchTalk.repository.achievement.UserCategoryRepository;
import com.example.sketchTalk.repository.achievement.UserSubRepository;
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
    private final SubCategoryRepository subCategoryRepository;
    private final UserSubRepository userSubRepository;

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

    // 도전과제 리스트 조회
    public List<CategoryListRes> getAcheivementList(Long userId, String status) {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryListRes> list = categories.stream().map(category -> {
            int total = subCategoryRepository.countByCategory(category);

            int completed = userSubRepository.countByUserSubKey_UserIdAndUserSubKey_SubIdInAndIsClearTrue(
                    userId,
                    subCategoryRepository.findIdsByCategory(category)
            );

            boolean isCompleted = (total > 0 && completed >= total);

            return new CategoryListRes(
                    category.getCategoryId(),
                    category.getName(),
                    completed,
                    total,
                    isCompleted
            );
        }).collect(Collectors.toList());

        return switch (status.toLowerCase()) {
            case "completed" -> list.stream()
                    .filter(CategoryListRes::isCompleted)
                    .collect(Collectors.toList());
            case "incomplete" -> list.stream()
                    .filter(c -> !c.isCompleted())
                    .collect(Collectors.toList());
            default -> list;
        };
    }

    // 도전과제 단일조회
    public CategoryDetailRes getAchievementDetail(Long userId, Long categoryId, String status) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도전과제입니다."));

        List<SubCategory> subs = subCategoryRepository.findByCategory(category);

        List<SubCategoryRes> subResList = subs.stream().map(sub -> {
            boolean completed = userSubRepository.existsByUserSubKey_UserIdAndUserSubKey_SubIdAndIsClearTrue(userId, sub.getSubId());
            return new SubCategoryRes(sub.getSubId(), sub.getName(), completed);
        }).collect(Collectors.toList());

        int total = subResList.size();
        int completedCount = (int) subResList.stream().filter(SubCategoryRes::completed).count();
        boolean isCompleted = (total > 0 && completedCount == total);

        List<SubCategoryRes> filteredSubs = switch (status.toLowerCase()) {
            case "completed" -> subResList.stream()
                    .filter(SubCategoryRes::completed)
                    .collect(Collectors.toList());
            case "incomplete" -> subResList.stream()
                    .filter(s -> !s.completed())
                    .collect(Collectors.toList());
            default -> subResList;
        };

        return new CategoryDetailRes(
                category.getCategoryId(),
                category.getName(),
                completedCount,
                total,
                isCompleted,
                filteredSubs
        );
    }

}





