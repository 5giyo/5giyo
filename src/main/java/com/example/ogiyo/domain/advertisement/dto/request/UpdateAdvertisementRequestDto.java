package com.example.ogiyo.domain.advertisement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UpdateAdvertisementRequestDto {
    private LocalDate startedAt;
    private LocalDate endedAt;
}
