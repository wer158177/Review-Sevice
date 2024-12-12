package com.hangha.reviewservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewResponse {
    //리뷰 조회를 위한 리스트 DTO
    private Long id;         // 리뷰 ID
    private Long userId;     // 작성자 ID
    private float score;       // 리뷰 점수
    private String content;  // 리뷰 내용
    private String imageUrl;
    private LocalDateTime createdAt;  // 생성 일시
}
