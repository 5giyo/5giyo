package com.example.ogiyo.menus.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ReviewRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private Long storeId; // 가게 ID
        private Long orderId; // 주문 ID (한 주문당 하나의 리뷰만 가능)
        private Integer rating; // 별점 (1~5)
        private String content; // 리뷰 내용
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {
        private String content; // 수정할 내용
        private Integer rating; // 수정할 별점
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long reviewId;
        private Long userId;
        private Long storeId;
        private Long orderId;
        private Integer rating;
        private String content;
        private Boolean isOwnerReplied;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}

