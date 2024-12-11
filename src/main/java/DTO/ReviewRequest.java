package DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewRequest {
    //리뷰 등록 DTO
    private Long userId;
    private Long score;
    private String content;
}
