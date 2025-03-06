package com.example.ogiyo.domain.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class UpdateReviewResponseDto {
    private String username;
    private final Byte rating;
    private final String content;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime time;

}
