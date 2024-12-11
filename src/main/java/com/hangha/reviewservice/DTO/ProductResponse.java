package com.hangha.reviewservice.DTO;


import lombok.Getter;

import java.util.List;


@Getter
public class ProductResponse {
    //리뷰 조회 반환
    private Long id;
    private float score;
    private Long Cursor;
    private List<ReviewResponse> reviews;

}
