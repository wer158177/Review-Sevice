package com.hangha.reviewservice.Service;


import com.hangha.reviewservice.DTO.ProductRequest;
import com.hangha.reviewservice.DTO.ProductResponse;
import com.hangha.reviewservice.DTO.ReviewRequest;
import com.hangha.reviewservice.Repository.ProductRepository;
import com.hangha.reviewservice.Repository.ReviewRepository;
import com.hangha.reviewservice.domain.Product;
import com.hangha.reviewservice.domain.Review;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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


//   //조회 메소드
//    public ProductResponse getProductReviews(Long productId, ProductRequest productRequest){
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품입니다."));
//        //리뷰 페이징 조회
//        List<Review> reviews = reviewRepository.findAllByProductId(productId);
//
//        return;
//    }

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
        String uploadImage = s3DummyService.uploadFile(reviewRequest.getImage());

        // 리뷰 생성 및 저장
        Review review = Review.builder()
                .userId(reviewRequest.getUserId())
                .score(reviewRequest.getScore())
                .content(reviewRequest.getContent())
                .imageFileName(uploadImage)
                .product(product)
                .build();

        reviewRepository.save(review);

        // 평균 점수 및 리뷰 수 업데이트
        updateProductScore(product);
    }


    // 리뷰 수정 메소드
    public void updateProductReview(Long productId, Long reviewId, ReviewRequest reviewRequest) {
        // 리뷰 수정 로직 구현 필요
    }

    // 리뷰 삭제 메소드
    public void deleteProductReview(Long productId, Long reviewId) {
        // 리뷰 삭제 로직 구현 필요
    }



    private void updateProductScore(Product product) {
        List<Review> reviews = reviewRepository.findAllByProductId(product.getId());
        double avgScore = reviews.stream()
                .mapToDouble(Review::getScore)
                .average()
                .orElse(0.0);

        long reviewCount = reviews.size();
        product.updateReviewStats(reviewCount, (float) avgScore);
    }
}
