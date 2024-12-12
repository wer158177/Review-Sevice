package com.hangha.reviewservice.Repository;

import com.hangha.reviewservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    //유저 조회
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    //리뷰수
    int countByProductId(Long productId);
    // 평점
    @Query("SELECT COALESCE(AVG(r.score),0 ) FROM  Review r where r.product.id=:productId")
    float averageScoreByProductId(@Param("productId")Long productId);

    // 커서
    @Query("""
    SELECT r FROM Review r 
    WHERE r.product.id = :productId 
    AND (:cursor=0 OR r.id < :cursor) 
    ORDER BY r.id DESC
""")
    List<Review> findByProductIdAndCursor(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            @Param("size") int size);
}