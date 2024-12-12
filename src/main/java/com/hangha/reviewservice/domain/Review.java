package com.hangha.reviewservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private String content;

    @Column
    private String imageUrl;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public Review(Long userId, Float score, String content, Product product, String imageUrl) {
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.product = product;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
    }

}
