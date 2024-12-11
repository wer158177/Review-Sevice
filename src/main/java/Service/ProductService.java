package Service;


import DTO.ProductRequest;
import DTO.ProductResponse;
import DTO.ReviewRequest;
import Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    // 조회 메소드
//    public ProductResponse getProductReviews(Long productId, ProductRequest productRequest){
//        //조회 로직 구현필요
//        return;
//    }

    // 작성 메소드
    public void saveProductReviews(Long productId, ReviewRequest reviewRequest){
        //작성 로직 구현 필요
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
