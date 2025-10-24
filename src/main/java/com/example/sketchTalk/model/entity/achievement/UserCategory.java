package com.example.sketchTalk.model.entity.achievement;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "User_category")
public class UserCategory {
    @EmbeddedId
    private UserCategoryKey userCategoryKey;

    @Column(name="is_clear")
    private boolean isClear;

    @Column(name="clear_date")
    private LocalDate clearDate;

    public UserCategory(UserCategoryKey userCategoryKey, boolean isClear, LocalDate clearDate) {
        this.userCategoryKey = userCategoryKey;
        this.isClear = isClear;
        this.clearDate = clearDate;
    }
}
