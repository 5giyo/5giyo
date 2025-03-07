package com.example.ogiyo.domain.advertisement.controller;

import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.advertisement.dto.request.CreateAdvertisementRequestDto;
import com.example.ogiyo.domain.advertisement.dto.request.UpdateAdvertisementRequestDto;
import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/advertisement")
public class AdvertisementController {
    private final AdvertisementService advertisementService;
    private final JwtUtil jwtUtil;

    // 테스트용
    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        return ResponseEntity.ok(advertisementService.findAllAdvertisements());
    }

    @PostMapping
    public ResponseEntity<Void> saveAdvertisement(RequestEntity<CreateAdvertisementRequestDto> request) {
        String jwt = request.getHeaders().getFirst("Authorization");
        CreateAdvertisementRequestDto dto = request.getBody();

        advertisementService.saveAdvertisement(
                jwtUtil.extractMemberId(jwt),
                dto.getStoreId(),
                dto.getStartedAt(),
                dto.getEndedAt()
        );
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{advertisementId}")
    public ResponseEntity<Void> updateAdvertisement(@PathVariable Long advertisementId, @RequestBody UpdateAdvertisementRequestDto dto) {
        advertisementService.updateAdvertisement(
                advertisementId,
                dto.getStartedAt(),
                dto.getEndedAt()
        );
        return ResponseEntity.ok().build();
    }

}
