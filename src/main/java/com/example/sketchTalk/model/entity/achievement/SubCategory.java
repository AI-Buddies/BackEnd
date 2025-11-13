package com.example.sketchTalk.model.entity.achievement;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "sub_category")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sub_id")
    private Long subId;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    public SubCategory(Category category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }
}
