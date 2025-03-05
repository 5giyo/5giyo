package com.example.ogiyo.domain.review.dto.response;

import com.example.ogiyo.domain.photo.dto.PhotoUrlResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class SaveReviewResponseDto {
    private String username;
    private final Byte rating;
    private final String content;
    private final List<PhotoUrlResponse> photoUrls;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime time;
}
