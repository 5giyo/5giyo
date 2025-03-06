package com.example.ogiyo.domain.advertisement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateAdvertisementRequestDto {
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
}
