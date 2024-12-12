package com.hangha.reviewservice.Controller;




import com.hangha.reviewservice.DTO.ReviewRequest;
import com.hangha.reviewservice.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ReviewController {

 private final ProductService productService;

    public ReviewController(ProductService productService) {
        this.productService = productService;
    }


//    리뷰 조회 api
//    @GetMapping("/{productId}/reviews")
//    public ResponseEntity<ProductResponse> getReviews(
//            @PathVariable Long productId,
//            @ModelAttribute ProductRequest productRequest) {
//
//        ProductResponse response = productService.getProductReviews(productId, productRequest);
//        return ResponseEntity.ok(response);
//    }

    //리뷰 작성 api
    @PostMapping("/{productId}/reviews")
    public ResponseEntity<String> saveReview(
            @PathVariable Long productId,
            @RequestBody ReviewRequest reviewRequest) {
        productService.saveProductReviews(productId, reviewRequest);
        return ResponseEntity.ok("작성 완료");
    }


    //리뷰 수정 api
    @PutMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<String> updateReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequest reviewRequest) {

        productService.updateProductReview(productId, reviewId, reviewRequest);
        return ResponseEntity.ok("수정성공");
    }

    //리뷰 삭제 api
    @DeleteMapping("/{productId}/reviews/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long productId,
            @PathVariable Long reviewId) {

        productService.deleteProductReview(productId, reviewId);
        return ResponseEntity.ok("삭제성공");
    }
}