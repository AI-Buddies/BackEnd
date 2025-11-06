package com.example.sketchTalk.model.entity.achievement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserCategoryKey implements Serializable {
    @Column
    private Long userId;

    @Column
    private Long CategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCategoryKey)) return false;
        UserCategoryKey that = (UserCategoryKey) o;
        return userId.equals(that.userId) && CategoryId.equals(that.CategoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, CategoryId);
    }
}
