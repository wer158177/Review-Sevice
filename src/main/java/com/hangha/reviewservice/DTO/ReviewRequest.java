package com.hangha.reviewservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ReviewRequest {
    //리뷰 등록 DTO
    private Long userId;
    private float score;
    private String content;
    private MultipartFile imageFileName;
}
