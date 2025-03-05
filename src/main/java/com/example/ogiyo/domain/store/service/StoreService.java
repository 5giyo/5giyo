package com.example.ogiyo.domain.store.service;

import com.example.ogiyo.domain.store.dto.response.GetStoreResponseDto;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import com.example.ogiyo.domain.store.dto.response.CreateStoreResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.example.ogiyo.domain.store.entity.Store.Status.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
//    private final MenuService menuService; //추후 추가

    public Store findByStoreWithUserInfo(Long storeId) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        // 사용자 검증

        return savedStore;
    }

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

        // 사장님은 가게를 최대 3개까지만 운영할 수 있습니다.
        // 사장님은 폐업시 가게를 추가로 등록할 수 있게 됩니다.
        // 메뉴 저장하는 부분 추후 추가
        Store store = Store.builder()
                .storeName(storeName)
                .operatingHours(operatingHours)
                .announcement(announcement)
                .minPrice(minPrice)
                .imageUrl(imageUrl)
                .status(OPEN)
                .build();

        Store savedStore = storeRepository.save(store);
        return new CreateStoreResponseDto(savedStore.getStoreId(), savedStore.getStoreName(), OPEN.toString());
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

        if (PERMANENTLY_CLOSED.toString().equals(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "폐업 할 수 없습니다.");
        }

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.changeStatus(valueOf(status));

        storeRepository.save(savedStore);
    }

    public void deleteStore(Long storeId) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.changeStatus(PERMANENTLY_CLOSED);

        storeRepository.save(savedStore);
    }
}
