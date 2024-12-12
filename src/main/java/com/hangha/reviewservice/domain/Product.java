package com.hangha.reviewservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reviewCount;

    @Column(nullable = false)
    private Float score;

    // 생성자 추가
    public Product(Long reviewCount, Float score) {
        this.reviewCount = reviewCount;
        this.score = score;
    }

    public void updateReviewStats(Long reviewCount, Float avgScore) {
        this.reviewCount = reviewCount;
        this.score = avgScore;
    }

}
