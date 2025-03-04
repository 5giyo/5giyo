package com.example.ogiyo.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class GetReviewResponseDto {
    private final Long reviewId;
    private final String userName;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime time;
    private final Byte rating;
    private final String content;
}
