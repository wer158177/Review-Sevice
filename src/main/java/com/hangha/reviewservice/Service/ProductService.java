package com.hangha.reviewservice.Service;


import com.hangha.reviewservice.DTO.ProductRequest;
import com.hangha.reviewservice.DTO.ProductResponse;
import com.hangha.reviewservice.DTO.ReviewRequest;
import com.hangha.reviewservice.DTO.ReviewResponse;
import com.hangha.reviewservice.Repository.ProductRepository;
import com.hangha.reviewservice.Repository.ReviewRepository;
import com.hangha.reviewservice.domain.Product;
import com.hangha.reviewservice.domain.Review;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final S3DummyService s3DummyService;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository, S3DummyService s3DummyService) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.s3DummyService = s3DummyService;
    }


   //조회 메소드
   @Transactional
   public ProductResponse getProductReviews(Long productId, Long cursor, int size) {
       if (!productRepository.existsById(productId)) {
           throw new IllegalArgumentException("존재하지 않는 제품입니다.");
       }

       // 총 리뷰 수와 평균 점수 계산
       int totalCount = reviewRepository.countByProductId(productId);
       float averageScore = reviewRepository.averageScoreByProductId(productId);

       List<Review> reviews = reviewRepository.findByProductIdAndCursor(productId, cursor, size);

       List<ReviewResponse> reviewResponses = reviews.stream()
               .map(this::convertToReviewResponse)
               .collect(Collectors.toList());

       // 마지막 커서 계산
       Long lastCursor = reviewResponses.isEmpty() ? null : reviewResponses.get(reviewResponses.size() - 1).getId();

       return new ProductResponse(productId, averageScore, lastCursor, totalCount, reviewResponses);
   }

    // DTO 변환 메서드
    private ReviewResponse convertToReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getUserId(),
                review.getScore(),
                review.getContent(),
                review.getImageUrl(),
                review.getCreatedAt()
        );
    }


    @Transactional
    public void saveProductReviews(Long productId, ReviewRequest reviewRequest) {
        // 제품 존재 확인
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));

        // 리뷰 중복 체크 (유저가 동일 제품에 리뷰를 이미 작성한 경우)
        if (reviewRepository.existsByUserIdAndProductId(reviewRequest.getUserId(), productId)) {
            throw new IllegalStateException("이미 리뷰를 작성했습니다.");
        }

        //이미지처리
        String uploadImage = s3DummyService.uploadFile(reviewRequest.getImageFileName());

        // 리뷰 생성 및 저장
        Review review = Review.builder()
                .userId(reviewRequest.getUserId())
                .score(reviewRequest.getScore())
                .content(reviewRequest.getContent())
                .imageUrl(uploadImage)
                .product(product)
                .build();

        reviewRepository.save(review);

    }




    // 리뷰 수정 메소드
    public void updateProductReview(Long productId, Long reviewId, ReviewRequest reviewRequest) {
        // 리뷰 수정 로직 구현 필요
    }

    // 리뷰 삭제 메소드
    public void deleteProductReview(Long productId, Long reviewId) {
        // 리뷰 삭제 로직 구현 필요
    }


}
