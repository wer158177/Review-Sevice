package com.hangha.reviewservice.Repository;

import com.hangha.reviewservice.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    Page<Review> findByProductIdOrderByIdDesc(Long productId, Pageable pageable);


    Page<Review> findByProductIdAndIdLessThanOrderByIdDesc(Long productId, Long cursor, Pageable pageable);


}