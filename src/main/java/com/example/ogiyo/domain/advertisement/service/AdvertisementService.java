package com.example.ogiyo.domain.advertisement.service;

import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.repository.AdvertisementRepository;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.example.ogiyo.domain.advertisement.entity.Advertisement.Status.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final StoreService storeService;
    private final AdvertisementRepository advertisementRepository;

    public void saveAdvertisement(Long ownerId, Long storeId, LocalDateTime startedAt, LocalDateTime endedAt) {

        Store savedStore = storeService.findByStoreWithOwnerId(ownerId, storeId);

        Advertisement advertisement = Advertisement.builder()
                .startedAt(startedAt)
                .endedAt(endedAt)
                .status(checkStatus(startedAt, endedAt))
                .store(savedStore)
                .build();

        advertisementRepository.save(advertisement);
    }

    public void updateAdvertisement(Long advertisementId, LocalDateTime startedAt, LocalDateTime endedAt) {
        Advertisement savedAdvertisement = advertisementRepository.findByAdvertisementIdOrElseThrow(advertisementId);

        savedAdvertisement.updateAdvertisement(
                startedAt,
                endedAt,
                checkStatus(startedAt, endedAt)
        );

        advertisementRepository.save(savedAdvertisement);
    }

    // 테스트용
    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

}
