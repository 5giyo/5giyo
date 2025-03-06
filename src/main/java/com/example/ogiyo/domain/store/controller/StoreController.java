package com.example.ogiyo.domain.store.controller;

import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.store.dto.request.UpdateStoreRequestDto;
import com.example.ogiyo.domain.store.dto.request.UpdateStoreStatusRequestDto;
import com.example.ogiyo.domain.store.dto.response.GetStoreResponseDto;
import com.example.ogiyo.domain.store.service.StoreService;
import com.example.ogiyo.domain.store.dto.request.CreateStoreRequestDto;
import com.example.ogiyo.domain.store.dto.response.CreateStoreResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {
    private final StoreService storeService;
    private final JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<?>> findStores(@RequestParam(required = false) String storeName) {
        return ResponseEntity.ok(storeService.findStores(storeName));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<GetStoreResponseDto> findStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.findStoreById(storeId));
    }

    @PostMapping
    public ResponseEntity<CreateStoreResponseDto> saveStore(RequestEntity<CreateStoreRequestDto> requestEntity) {
        String jwt = requestEntity.getHeaders().getFirst("Authorization");
        CreateStoreRequestDto dto = requestEntity.getBody();
        CreateStoreResponseDto responseDto = storeService.saveStore(
                jwtUtil.extractMemberId(jwt),
                dto.getStoreName(),
                dto.getOperatingHours(),
                dto.getAnnouncement(),
                dto.getMinPrice(),
                dto.getImageUrl());

        URI location = URI.create("/api/v1/stores/" + responseDto.getStoreId());
        return ResponseEntity.created(location).body(responseDto);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<Void> updateStore(@PathVariable Long storeId, @RequestBody UpdateStoreRequestDto dto) {
        storeService.updateStore(storeId,
                dto.getStoreName(),
                dto.getOperatingHours(),
                dto.getAnnouncement(),
                dto.getMinPrice(),
                dto.getImageUrl());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<Void> updateStoreStatus(@PathVariable Long storeId, @RequestBody UpdateStoreStatusRequestDto dto) {
        storeService.updateStore(
                storeId,
                dto.getStatus());

        return ResponseEntity.noContent().build();
    }

    // UserAuth 추가
    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ResponseEntity.ok().build();
    }
}
