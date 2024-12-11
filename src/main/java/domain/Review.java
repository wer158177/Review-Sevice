package domain;

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

    private LocalDateTime createdAt;

    // 연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    public Review(Long userId, Float score, String content, Product product) {
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.product = product;
        this.createdAt = LocalDateTime.now();
    }

}
