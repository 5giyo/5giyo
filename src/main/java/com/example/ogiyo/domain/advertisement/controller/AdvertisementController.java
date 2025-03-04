package com.example.ogiyo.domain.advertisement.controller;

import com.example.ogiyo.domain.advertisement.dto.request.CreateAdvertisementRequestDto;
import com.example.ogiyo.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<?> saveAdvertisement(@RequestBody CreateAdvertisementRequestDto dto) {
        advertisementService.saveAdvertisement(
                dto.getStoreId(),
                dto.getStartedAt(),
                dto.getEndedAt()
        );
        return ResponseEntity.ok().build();
    }

}
