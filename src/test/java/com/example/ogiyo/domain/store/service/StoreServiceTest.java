package com.example.ogiyo.domain.store.service;

import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.repository.MemberRepository;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.store.dto.response.CreateStoreResponseDto;
import com.example.ogiyo.domain.store.dto.response.GetStoreResponseDto;
import com.example.ogiyo.domain.store.dto.response.GetStoresResponseDto;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    StoreRepository storeRepository;
    @Mock
    MemberService memberService;
    @InjectMocks
    StoreService storeService;

    @Test
    void 가게_다건_조회() {
        // given
        String storeName = "치킨마루";

        List<GetStoresResponseDto> mockStoresWithName = List.of(
                new GetStoresResponseDto(1L, "치킨마루", 2000L, "kkokkio@naver.com", "OPEN"),
                new GetStoresResponseDto(5L, "치킨마루", 2000L, "kkokkio@naver.com", "OPEN"));

        List<GetStoresResponseDto> mockStoresWithNoName = List.of(
                new GetStoresResponseDto(1L, "치킨마루", 2000L, "kkokkio@naver.com", "OPEN"),
                new GetStoresResponseDto(2L, "피자마루", 2000L, "kkokkio@naver.com", "OPEN"),
                new GetStoresResponseDto(5L, "치킨마루", 2000L, "kkokkio@naver.com", "OPEN"));

        given(storeRepository.findByStoreNameToDto(storeName))
                .willReturn(mockStoresWithName);

        given(storeRepository.findAllToDto())
                .willReturn(mockStoresWithNoName);

        // when
        List<GetStoresResponseDto> withNoName = storeService.findStores(null);
        List<GetStoresResponseDto> withName = storeService.findStores(storeName);

        // then
        Assertions.assertThat(withName).size().isEqualTo(2);
        Assertions.assertThat(withNoName).size().isEqualTo(3);
    }

    @Test
    void 가게_단건_조회() {
        // given
        Long storeId = 1L;

        List<Menu> menus = List.of(
                Menu.builder().menuName("치킨").build(),
                Menu.builder().menuName("피자").build()
        );

        Store store = Store.builder()
                .storeName("치킨마루")
                .operatingHours("13:00 ~ 20:00")
                .announcement("공지입니다.")
                .minPrice(2000L)
                .imageUrl("kkokkio@naver.com")
                .status(Store.Status.OPEN)
                .menus(menus)
                .build();

        given(storeRepository.findByIdOrElseThrow(storeId)).willReturn(store);

        // when
        GetStoreResponseDto result = storeService.findStoreById(storeId);

        // then
        assertNotNull(result);
    }

    @Test
    void 가게_최대_보유_갯수_넘지않으면_성공() {
        // given
        String storeName = "치킨마루";
        String operationHours = "13:00 ~ 20:00";
        String announcement = "공지입니다.";
        Long minPrice = 2000L;
        String imageUrl = "kkokkio@naver.com";

        Member owner = Member.builder()
                .name("a")
                .email("aa@a.com")
                .password("aaaa")
                .role(MemberRole.OWNER)
                .build();

        Long ownerId = 1L;
        ReflectionTestUtils.setField(owner, "id", ownerId);
        ReflectionTestUtils.setField(owner, "countOwnedStore", 2);

        given(memberService.findById(1L)).willReturn(Optional.of(owner));
        given(storeRepository.save(any(Store.class)))
                .willAnswer(invocation -> {
                    Store savedStore = invocation.getArgument(0);
                    ReflectionTestUtils.setField(savedStore, "id", 1L);
                    return savedStore;
                });
        // when
        CreateStoreResponseDto result = storeService.saveStore(ownerId, storeName, operationHours, announcement, minPrice, imageUrl);

        // then
        assertNotNull(result);
    }

    @Test
    void 가게_최대_보유_갯수_넘으면_예외() {
        // given
        String storeName = "치킨마루";
        String operationHours = "13:00 ~ 20:00";
        String announcement = "공지입니다.";
        Long minPrice = 2000L;
        String imageUrl = "kkokkio@naver.com";

        Member owner = Member.builder()
                .name("a")
                .email("aa@a.com")
                .password("aaaa")
                .role(MemberRole.OWNER)
                .build();

        Long ownerId = 1L;
        ReflectionTestUtils.setField(owner, "id", ownerId);

        // 사장님이 소유한 가게 수 = 3
        ReflectionTestUtils.setField(owner, "countOwnedStore", 3);

        given(memberService.findById(1L)).willReturn(Optional.of(owner));
        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            storeService.saveStore(ownerId, storeName, operationHours, announcement, minPrice, imageUrl);
        });

        // then
        assertEquals("가게는 최대 3개까지 등록할 수 있습니다.", exception.getReason());
    }

    @Test
    void 상점_상태_업데이트시_성공() {
        // given
        String storeName = "치킨마루";
        String operationHours = "13:00 ~ 20:00";
        String announcement = "공지입니다.";
        Long minPrice = 2000L;
        String imageUrl = "kkokkio@naver.com";
        Long storeId = 1L;

        Store store = Store.builder()
                .storeName(storeName)
                .operatingHours(operationHours)
                .announcement(announcement)
                .minPrice(minPrice)
                .imageUrl(imageUrl)
                .status(Store.Status.OPEN)
                .build();

        ReflectionTestUtils.setField(store, "id", storeId);

        given(storeRepository.findByIdOrElseThrow(storeId)).willReturn(store);
        given(storeRepository.save(any(Store.class)))
                .willAnswer(invocation -> {
                    Store savedStore = invocation.getArgument(0);
                    ReflectionTestUtils.setField(savedStore, "id", 1L);
                    return savedStore;
                });

        // when & then
        storeService.updateStore(storeId, "CLOSED");
    }

    @Test
    void 상점_상태_폐업으로_업데이트시_예외() {
        // given
        Long storeId = 1L;

        // when
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            storeService.updateStore(storeId, "PERMANENTLY_CLOSED");
        });
        // then
        assertEquals("폐업 할 수 없습니다.", exception.getReason());
    }

    @Test
    void deleteStore() {
        // given
        String storeName = "치킨마루";
        String operationHours = "13:00 ~ 20:00";
        String announcement = "공지입니다.";
        Long minPrice = 2000L;
        String imageUrl = "kkokkio@naver.com";
        Long storeId = 1L;

        Member owner = Member.builder()
                .name("a")
                .email("aa@a.com")
                .password("aaaa")
                .role(MemberRole.OWNER)
                .build();

        Store store = Store.builder()
                .storeName(storeName)
                .operatingHours(operationHours)
                .announcement(announcement)
                .minPrice(minPrice)
                .imageUrl(imageUrl)
                .status(Store.Status.OPEN)
                .owner(owner)
                .build();

        ReflectionTestUtils.setField(owner, "countOwnedStore", 3);
        ReflectionTestUtils.setField(store, "id", storeId);

        given(storeRepository.findByIdOrElseThrow(storeId)).willReturn(store);

        // when & then
        storeService.deleteStore(storeId);
    }
}