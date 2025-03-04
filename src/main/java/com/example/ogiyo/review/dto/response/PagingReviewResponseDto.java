package com.example.ogiyo.review.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class PagingReviewResponseDto {
    private final List<GetReviewResponseDto> reviews;
    private final int totalCount;
    private final int totalPages;
    private final int currentPage;
}
