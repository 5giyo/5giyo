package com.example.ogiyo.domain.advertisement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateAdvertisementRequestDto {
    private Long storeId;

    // 항상 새벽 6시 시작
    private LocalDateTime startedAt;

    // 항상 새벽 5시 59분 종료
    private LocalDateTime endedAt;
}
