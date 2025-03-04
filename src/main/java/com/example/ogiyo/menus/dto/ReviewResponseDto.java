package com.example.ogiyo.menus.dto;

import lombok.*;

import java.time.LocalDateTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder

    public class ReviewResponseDto {
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
