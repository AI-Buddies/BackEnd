package com.example.sketchTalk.model.entity.achievement;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserSubKey implements Serializable {
    @Column
    private Long userId;

    @Column
    private Long sub_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSubKey)) return false;
        UserSubKey that = (UserSubKey) o;
        return userId.equals(that.userId) && sub_id.equals(that.sub_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sub_id);
    }

}
