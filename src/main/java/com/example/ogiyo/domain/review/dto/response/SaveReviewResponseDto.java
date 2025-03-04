package com.example.ogiyo.domain.review.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SaveReviewResponseDto {
    private String username;
    private final Byte rating;
    private final String content;

}
