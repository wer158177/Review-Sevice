package com.hangha.reviewservice.Service;


import com.hangha.reviewservice.Repository.ProductRepository;
import com.hangha.reviewservice.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReviewSeviceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    @DisplayName("제품 조회?")
    void getProductException(){
        //Given
        Long productId = 1L;


    }

}
