package com.example.ogiyo.domain.ceoReview.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCeoReviewRequestDto {
    @NotBlank(message = "리뷰내용은 비워둘 수 없습니다.")
    @Size(min = 5, message = "최소 5자 이상 입력해주세요.")
    private String content;
}
