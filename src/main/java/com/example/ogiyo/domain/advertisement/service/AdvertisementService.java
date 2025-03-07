package com.example.ogiyo.domain.advertisement.service;

import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import com.example.ogiyo.domain.advertisement.repository.AdvertisementRepository;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static com.example.ogiyo.domain.advertisement.entity.Advertisement.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final StoreService storeService;
    private final AdvertisementRepository advertisementRepository;

    public void saveAdvertisement(Long ownerId, Long storeId, LocalDate startedAt, LocalDate endedAt) {

        Store savedStore = storeService.findByStoreWithOwnerId(ownerId, storeId);

        LocalDateTime started = LocalDateTime.of(startedAt, LocalTime.of(6, 0));
        LocalDateTime ended = LocalDateTime.of(endedAt, LocalTime.of(5, 59));
        Advertisement advertisement = Advertisement.builder()
                .startedAt(started)
                .endedAt(ended)
                .status(Status.checkStatus(started, ended))
                .store(savedStore)
                .build();

        advertisementRepository.save(advertisement);
    }

    public void updateAdvertisement(Long advertisementId, LocalDate startedAt, LocalDate endedAt) {
        Advertisement savedAdvertisement = advertisementRepository.findByAdvertisementIdOrElseThrow(advertisementId);

        LocalDateTime started = LocalDateTime.of(startedAt, LocalTime.of(6, 0));
        LocalDateTime ended = LocalDateTime.of(endedAt, LocalTime.of(5, 59));

        savedAdvertisement.updateAdvertisement(
                started,
                ended,
                Status.checkStatus(started, ended)
        );

        advertisementRepository.save(savedAdvertisement);
    }

    // 테스트용
    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

}
