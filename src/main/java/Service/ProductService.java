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
    public void getProductReviews(Long productId,ProductRequest productRequest){

        return;
    }

    // 작성 메소드
    public void saveProductReviews(Long productId, ReviewRequest reviewRequest){

    }

    // 수정 메소드
    public void updateProductReview(Long productId, ReviewRequest reviewRequest){

    }

    // 삭제 메소드
    public void deleteProductReview(Long productId, Long reviewId, ReviewRequest reviewRequest){

    }

}
