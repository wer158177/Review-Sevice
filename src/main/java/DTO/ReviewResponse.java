package DTO;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponse {
    //리뷰 조회를 위한 리스트 DTO
    private Long id;         // 리뷰 ID
    private Long userId;     // 작성자 ID
    private int score;       // 리뷰 점수
    private String content;  // 리뷰 내용
    private LocalDateTime createdAt;  // 생성 일시
}
