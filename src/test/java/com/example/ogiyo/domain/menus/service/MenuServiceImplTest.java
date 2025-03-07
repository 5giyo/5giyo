package com.example.ogiyo.domain.menus.service;

import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.menus.dto.CreateMenuResponseDto;
import com.example.ogiyo.domain.menus.dto.MenuRequest;
import com.example.ogiyo.domain.menus.dto.MenuResponse;
import com.example.ogiyo.domain.menus.entity.*;
import com.example.ogiyo.domain.menus.enums.Status;
import com.example.ogiyo.domain.menus.repository.*;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import com.example.ogiyo.domain.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuServiceImplTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private OrderCountRepository orderCountRepository;

    @Mock
    private LikeCountRepository likeCountRepository;

    @Mock
    private SearchCountRepository searchCountRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 메뉴_생성_테스트() {
        // Given
        String token = "valid-token";
        Long storeId = 1L;
        Long ownerId = 1L;
        String category = "Main";
        String menuName = "Burger";
        Integer price = 10000;
        String menuOption = "Spicy";
        Status status = Status.AVAILABLE;

        Store store = Store.builder().id(storeId).build();
        OrderCount orderCount = new OrderCount();
        LikeCount likeCount = new LikeCount();
        SearchCount searchCount = new SearchCount();

        Menu menu = Menu.builder()
                .store(store)
                .category(category)
                .menuName(menuName)
                .price(price)
                .status(status)
                .menuOption(menuOption)
                .orderCount(orderCount)
                .likeCount(likeCount)
                .searchCount(searchCount)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        when(jwtUtil.extractMemberId(token)).thenReturn(ownerId);
        when(storeService.findByStoreWithOwnerId(ownerId, storeId)).thenReturn(store);
        when(orderCountRepository.save(any(OrderCount.class))).thenReturn(orderCount);
        when(likeCountRepository.save(any(LikeCount.class))).thenReturn(likeCount);
        when(searchCountRepository.save(any(SearchCount.class))).thenReturn(searchCount);
        when(menuRepository.save(any(Menu.class))).thenReturn(menu);

        // When
        CreateMenuResponseDto response = menuService.createMenu(token, storeId, category, menuName, price, menuOption, status);

        // Then
        assertNotNull(response);
        assertEquals(menu.getId(), response.getMenuId());
        assertEquals(storeId, response.getStoreId());
        assertEquals(category, response.getCategory());
        assertEquals(menuName, response.getMenuName());
        assertEquals(price, response.getPrice());
        assertEquals(menuOption, response.getMenuOption());
        assertEquals(status, response.getStatus());
        assertEquals(menu.getCreatedAt(), response.getCreatedAt());
        assertEquals(menu.getModifiedAt(), response.getModifiedAt());

        verify(jwtUtil, times(1)).extractMemberId(token);
        verify(storeService, times(1)).findByStoreWithOwnerId(ownerId, storeId);
        verify(orderCountRepository, times(1)).save(any(OrderCount.class));
        verify(likeCountRepository, times(1)).save(any(LikeCount.class));
        verify(searchCountRepository, times(1)).save(any(SearchCount.class));
        verify(menuRepository, times(1)).save(any(Menu.class));
    }

    @Test
    void 메뉴_조회_테스트() {
        // Given
        Long menuId = 1L;
        Menu menu = Menu.builder().id(menuId).build();

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // When
        Menu result = menuService.getMenu(menuId);

        // Then
        assertNotNull(result);
        assertEquals(menuId, result.getId());

        verify(menuRepository, times(1)).findById(menuId);
    }

    @Test
    void 주문횟수_증가_테스트() {
        // Given
        Long menuId = 1L;
        OrderCount orderCount = new OrderCount();
        Menu menu = Menu.builder().id(menuId).orderCount(orderCount).build();

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // When
        menuService.increaseOrderCount(menuId);

        // Then
        assertEquals(1, orderCount.getOrderCount());

        verify(menuRepository, times(1)).findById(menuId);
    }

    @Test
    void 메뉴_스토어_동시검색_테스트() {
        // Given
        Long storeId = 1L;
        String category = "Main";
        Store store = Store.builder().id(storeId).build();
        Menu menu = Menu.builder().store(store).category(category).build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(menuRepository.findByStoreIdAndCategory(storeId, category)).thenReturn(Collections.singletonList(menu));

        // When
        List<MenuResponse> result = menuService.getMenusByStore(storeId, category);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(menu.getCategory(), result.get(0).getCategory());

        verify(storeRepository, times(1)).findById(storeId);
        verify(menuRepository, times(1)).findByStoreIdAndCategory(storeId, category);
    }


    @Test
    void 메뉴_삭제_테스트() {
        // Given
        Long menuId = 1L;
        Long ownerId = 1L;
        Store store = Store.builder().id(1L).build();
        Menu menu = Menu.builder().id(menuId).store(store).status(Status.AVAILABLE).build();

        when(menuRepository.findWithStoreAndOwnerById(menuId)).thenReturn(Optional.of(menu));

        // When
        menuService.deleteMenu(menuId, ownerId);

        // Then
        assertEquals(Status.DELETED, menu.getStatus());

        verify(menuRepository, times(1)).findWithStoreAndOwnerById(menuId);
    }
    // 테스트 끝
}