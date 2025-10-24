package com.example.sketchTalk.repository.achievement;

import com.example.sketchTalk.model.entity.achievement.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = """
                    SELECT
                        C.id AS cid as categoryId, C.name as categoryName
                    FROM
                        category C
                    JOIN
                        sub_category S ON C.id = S.category_id
                    LEFT JOIN
                        user_sub U ON S.id = U.sub_id AND U.user_id = :userId
                    WHERE C.id IN :idList
                    GROUP BY
                        C.id, Cname
                    HAVING
                        COUNT(S.id) = SUM(CASE WHEN U.is_clear = TRUE THEN 1 ELSE 0 END);
                    
                    """,
            nativeQuery = true
    )
    List<Object[]> findCompletedCategoryByUserAndList(@Param("userId") Long userId, @Param("idList") List<Long> idList);
}
