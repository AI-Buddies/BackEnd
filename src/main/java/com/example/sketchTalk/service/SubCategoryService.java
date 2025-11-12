package com.example.sketchTalk.service;

import com.example.sketchTalk.dto.subcategory.out.SubCategoryNamesDTO;
import com.example.sketchTalk.model.entity.achievement.SubCategory;
import com.example.sketchTalk.model.entity.achievement.UserSub;
import com.example.sketchTalk.model.entity.achievement.UserSubKey;
import com.example.sketchTalk.repository.achievement.SubCategoryRepository;
import com.example.sketchTalk.repository.achievement.UserSubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final UserSubRepository userSubRepository;

    public List<SubCategoryNamesDTO> findAllSubCategories() {
        List<SubCategory> subCategories = subCategoryRepository.findAll();
        return subCategories.stream()
                .map(subCategory -> new SubCategoryNamesDTO(subCategory.getSubId(), subCategory.getCategory(), subCategory.getName()))
                .collect(Collectors.toList());
    }

    public List<Long> updateSubCategory(List<SubCategoryNamesDTO> subs) {
        Set<Long> categoryNeedToCheck = new HashSet<>();
        Long userId = 1L;
        subs.forEach(subDTO -> {
            categoryNeedToCheck.add(subDTO.category().getCategoryId());
            UserSubKey userSubKey = new UserSubKey(userId, subDTO.subId());
            UserSub userSub = new UserSub(userSubKey, true, LocalDate.now());
            userSubRepository.save(userSub);
        });
        return new ArrayList<>(categoryNeedToCheck);
    }

    public List<Long> subOfCategory(Long categoryId) {
        return subCategoryRepository.findAll().stream()
                .map(SubCategory::getSubId)
                .collect(Collectors.toList());
    }
}
