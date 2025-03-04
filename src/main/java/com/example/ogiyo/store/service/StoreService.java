package com.example.ogiyo.store.service;

import com.example.ogiyo.store.dto.response.CreateStoreResponseDto;
import com.example.ogiyo.store.dto.response.GetStoreResponseDto;
import com.example.ogiyo.store.entity.Store;
import com.example.ogiyo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
//    private final MenuService menuService; //추후 추가

    public List<?> findStores(String storeName) {
        if (storeName == null) {
            return storeRepository.findAll();
        }

        return storeRepository.findByStoreNameToDto(storeName);
    }

    public GetStoreResponseDto findStoreById(Long storeId) {
        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        return new GetStoreResponseDto(
                savedStore.getStoreId(),
                savedStore.getStoreName(),
                savedStore.getOperatingHours(),
                savedStore.getAnnouncement(),
                savedStore.getMinPrice(),
                savedStore.getImageUrl(),
                savedStore.getStatus().toString());
    }

    public CreateStoreResponseDto saveStore(String storeName, String operatingHours, String announcement, Long minPrice, String imageUrl) {
        // 메뉴 저장하는 부분 추후 추가
        Store store = Store.builder()
                .storeName(storeName)
                .operatingHours(operatingHours)
                .announcement(announcement)
                .minPrice(minPrice)
                .imageUrl(imageUrl)
                .status(Store.Status.OPEN)
                .build();

        // 운영시간으로 영업, 마감, 폐업 선택

        Store savedStore = storeRepository.save(store);
        return new CreateStoreResponseDto(savedStore.getStoreId(), savedStore.getStoreName(), Store.Status.OPEN.toString());
    }

    public void updateStore(Long storeId, String storeName, String operatingHours, String announcement, Long minPrice, String imageUrl) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.updateStore(
                storeName,
                operatingHours,
                announcement,
                minPrice,
                imageUrl);

        storeRepository.save(savedStore);
    }

    public void updateStore(Long storeId, String status) {

        if (Store.Status.PERMANENTLY_CLOSED.toString().equals(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "폐업 할 수 없습니다.");
        }

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.changeStatus(Store.Status.valueOf(status));

        storeRepository.save(savedStore);
    }

    public void deleteStore(Long storeId) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.changeStatus(Store.Status.PERMANENTLY_CLOSED);

        storeRepository.save(savedStore);
    }
}
