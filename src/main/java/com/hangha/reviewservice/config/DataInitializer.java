package com.hangha.reviewservice.config;

import com.hangha.reviewservice.Repository.ProductRepository;
import com.hangha.reviewservice.domain.Product;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer {

    private final ProductRepository productRepository;

    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 애플리케이션 시작 시 실행
    @PostConstruct
    public void init() {

        // 제품 데이터 초기화
        Product product1 = new Product();
        Product product2 = new Product();

        productRepository.saveAll(List.of(product1, product2));
        System.out.println("제품 데이터 입력");
    }
}
