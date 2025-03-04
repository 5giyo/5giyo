package com.example.ogiyo.domain.advertisement.service;

import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.repository.AdvertisementRepository;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final StoreService storeService;
    private final AdvertisementRepository advertisementRepository;

    public void saveAdvertisement(Long storeId, LocalDateTime startedAt, LocalDateTime endedAt) {

        Store savedStore = storeService.findByStoreWithUserInfo(storeId);

        Advertisement advertisement = Advertisement.builder()
                .startedAt(startedAt)
                .endedAt(endedAt)
                .status(Advertisement.Status.checkStatus(startedAt, endedAt))
                .store(savedStore)
                .build();

        advertisementRepository.save(advertisement);
    }

}
