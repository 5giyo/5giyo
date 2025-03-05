package com.example.ogiyo.domain.advertisement.controller;

import com.example.ogiyo.domain.advertisement.dto.request.CreateAdvertisementRequestDto;
import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    // 테스트용
    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.findAllAdvertisements());
    }

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
