package com.example.ogiyo.domain.review.dto.response;

import com.example.ogiyo.domain.photo.dto.PhotoUrlResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetReviewResponseDto {
    private final Long reviewId;
    private final String userName;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime time;
    private final Byte rating;
    private final List<PhotoUrlResponse> photoUrls;
    private final String content;

    private final String ceoComment;
}
