package com.hangha.reviewservice.Repository;

import com.hangha.reviewservice.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    List<Review> findAllByProductId(Long productId);
}