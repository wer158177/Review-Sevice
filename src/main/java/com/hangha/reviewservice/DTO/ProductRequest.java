package com.hangha.reviewservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {
    //리뷰 조회 요청 DTO
    private Long productId;
    private Long cursor;
    private int size;
}
