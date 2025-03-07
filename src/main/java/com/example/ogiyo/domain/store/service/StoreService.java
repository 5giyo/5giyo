package com.example.ogiyo.domain.store.service;

import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.store.dto.response.GetStoreResponseDto;
import com.example.ogiyo.domain.store.dto.response.GetStoresResponseDto;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import com.example.ogiyo.domain.store.dto.response.CreateStoreResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static com.example.ogiyo.domain.store.entity.Store.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MemberService memberService;

    public List<GetStoresResponseDto> findStores(String storeName) {

        if (storeName == null) {
            return storeRepository.findAllToDto();
        }

        return storeRepository.findByStoreNameToDto(storeName);
    }

    public GetStoreResponseDto findStoreById(Long storeId) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        return new GetStoreResponseDto(
                savedStore.getId(),
                savedStore.getStoreName(),
                savedStore.getOperatingHours(),
                savedStore.getAnnouncement(),
                savedStore.getMinPrice(),
                savedStore.getImageUrl(),
                savedStore.getStatus().toString(),
                savedStore.getMenus().stream()
                        .map(Menu::getMenuName)
                        .toList());
    }

    public Store findStore(Long storeId) {
        return storeRepository.findByIdOrElseThrow(storeId);
    }

    @Transactional
    public CreateStoreResponseDto saveStore(Long ownerId, String storeName, String operatingHours, String announcement, Long minPrice, String imageUrl) {

        // 사장님은 가게를 최대 3개까지만 운영할 수 있습니다.
        Member owner = validateOwner(ownerId);

        if (owner.getCountOwnedStore() >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가게는 최대 3개까지 등록할 수 있습니다.");
        }

        // 메뉴 저장하는 부분 추후 추가
        Store store = Store.builder()
                .storeName(storeName)
                .operatingHours(operatingHours)
                .announcement(announcement)
                .minPrice(minPrice)
                .imageUrl(imageUrl)
                .status(Status.OPEN)
                .owner(owner)
                .build();

        Store savedStore = storeRepository.save(store);
        savedStore.getOwner().addCountOwnedStore();
        return new CreateStoreResponseDto(savedStore.getId(), savedStore.getStoreName(), Status.OPEN.toString());
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

        if (Status.PERMANENTLY_CLOSED.toString().equals(status)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "폐업 할 수 없습니다.");
        }

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        savedStore.changeStatus(Status.valueOf(status));

        storeRepository.save(savedStore);
    }

    @Transactional
    public void deleteStore(Long storeId) {

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        // 사장님은 폐업시 가게를 추가로 등록할 수 있게 됩니다.
        savedStore.changeStatus(Status.PERMANENTLY_CLOSED);
        savedStore.getOwner().removeCountOwnedStore();
    }

    public Store findByStoreWithOwnerId(Long ownerId, Long storeId) {

        Member savedMember = validateOwner(ownerId);

        Store savedStore = storeRepository.findByIdOrElseThrow(storeId);

        if (!savedStore.getOwner().getId().equals(savedMember.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님이 등록한 가게가 아닙니다.");
        }

        return savedStore;
    }

    private Member validateOwner(Long ownerId) {
        Member savedMember = memberService.findById(ownerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        if (!savedMember.getRole().equals(MemberRole.OWNER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "사장님이 아닙니다.");
        }
        return savedMember;
    }
}
