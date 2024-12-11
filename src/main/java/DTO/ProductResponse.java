package DTO;


import lombok.Getter;

import java.util.List;


@Getter
public class ProductResponse {
    private Long id;
    private float score;
    private Long Cursor;
    private List<ReviewResponse> reviews;

}
