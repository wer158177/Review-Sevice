package com.hangha.reviewservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
public class ProductResponse {
    //리뷰 조회 반환
    private Long id;
    private float score;
    private Long Cursor;
    private int totalCount;
    private List<ReviewResponse> reviews;

}
