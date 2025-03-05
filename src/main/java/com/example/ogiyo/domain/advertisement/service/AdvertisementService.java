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

    public void saveAdvertisement(Long storeId, LocalDateTime startedAt, LocalDateTime endedAt) {

        Store savedStore = storeService.findByStoreWithUserInfo(storeId);

        Advertisement advertisement = Advertisement.builder()
                .startedAt(startedAt)
                .endedAt(endedAt)
                .status(checkStatus(startedAt, endedAt))
                .store(savedStore)
                .build();

        advertisementRepository.save(advertisement);
    }

    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

}
