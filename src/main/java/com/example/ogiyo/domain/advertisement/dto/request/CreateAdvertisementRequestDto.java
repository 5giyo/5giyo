package com.example.ogiyo.domain.advertisement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateAdvertisementRequestDto {
    private Long storeId;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
