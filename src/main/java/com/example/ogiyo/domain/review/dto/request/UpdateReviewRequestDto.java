package com.example.ogiyo.domain.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateReviewRequestDto {
    @NotNull
    @Min(value = 1, message = "별점은 최소 1점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점 이하이어야 합니다.")
    private Byte rating;

    @NotBlank(message = "리뷰내용은 비워둘 수 없습니다.")
    @Size(min = 5, message = "최소 5자 이상 입력해주세요.")
    private String content;
}
