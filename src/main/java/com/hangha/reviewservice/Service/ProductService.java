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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        // 제품이 존재하는지 체크
        if (!productRepository.existsById(productId)) {
            throw new IllegalArgumentException("존재하지 않는 제품입니다.");
        }

        // 리뷰 페이징 조회
        Page<Review> reviewPage = (cursor == null || cursor == 0) ?
                reviewRepository.findByProductIdOrderByIdDesc(productId, PageRequest.of(0, size)) :
                reviewRepository.findByProductIdAndIdLessThanOrderByIdDesc(productId, cursor, PageRequest.of(0, size));

        // 결과 처리
        List<ReviewResponse> reviewResponses = reviewPage.getContent().stream()
                .map(this::convertToReviewResponse)
                .collect(Collectors.toList());

        // 마지막 커서 계산 (다음 페이지를 위해)
        Long lastCursor = !reviewResponses.isEmpty() ? reviewResponses.get(reviewResponses.size() - 1).getId() : null;

        // 총 리뷰 수와 평균 점수 계산
        int totalCount = reviewRepository.countByProductId(productId);
        float averageScore = Math.round(reviewRepository.averageScoreByProductId(productId) * 10) / 10.0f;


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

        //1~5점까지만 입력가능
        if (reviewRequest.getScore() > 5 || reviewRequest.getScore() < 1) {
            throw new IllegalStateException("1~5사이만 입력가능합니다");
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
