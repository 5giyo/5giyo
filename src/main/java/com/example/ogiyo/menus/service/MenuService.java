package com.example.ogiyo.menus.service;

import com.example.ogiyo.menus.dto.MenuRequest;
import com.example.ogiyo.menus.dto.MenuResponse;
import com.example.ogiyo.menus.entity.LikeCount;
import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.entity.OrderCount;
import com.example.ogiyo.menus.entity.SearchCount;
import com.example.ogiyo.menus.repository.LikeCountRepository;
import com.example.ogiyo.menus.repository.MenuRepository;
import com.example.ogiyo.menus.repository.OrderCountRepository;
import com.example.ogiyo.menus.repository.SearchCountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
//    private final StoreRepository storeRepository;
    private final OrderCountRepository orderCountRepository;
    private final LikeCountRepository likeCountRepository;
    private final SearchCountRepository searchCountRepository;

    // 메뉴 추가
    public MenuResponse createMenu(MenuRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        // 기본적인 Count 엔티티 생성
        OrderCount orderCount = orderCountRepository.save(new OrderCount(null, 0));
        LikeCount likeCount = likeCountRepository.save(new LikeCount(null, 0));
        SearchCount searchCount = searchCountRepository.save(new SearchCount(null, 0));

        Menu menu = Menu.builder()
                .store(store)
                .category(request.getCategory())
                .menuName(request.getMenuName())
                .price(request.getPrice())
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .status(request.getStatus())
                .searchCount(0)
                .option(request.getOption())
                .orderCount(orderCount)
                .likeCount(likeCount)
                .searchCountEntity(searchCount)
                .build();

        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    //  특정 메뉴 조회
    public MenuResponse getMenu(Long menuId) {
        java.awt.Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        return new MenuResponse(menu);
    }

    //  특정 가게의 메뉴 목록 조회
    public List<MenuResponse> getMenusByStore(Long storeId) {
        return menuRepository.findByStore_StoreId(storeId)
                .stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }

    //  메뉴 수정
    public MenuResponse updateMenu(Long menuId, MenuRequest request) {
        java.awt.Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menu.setCategory(request.getCategory());
        menu.setMenuName(request.getMenuName());
        menu.setPrice(request.getPrice());
        menu.setStatus(request.getStatus());
        menu.setOption(request.getOption());
        menu.setModifiedAt(LocalDateTime.now());

        return new MenuResponse(menu);
    }

    // 메뉴 삭제
    public void deleteMenu(Long menuId) {
        java.awt.Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menuRepository.delete(menu);
    }

    // 검색 기능 (검색 횟수 증가 포함)
    public List<MenuResponse> searchMenus(String keyword) {
        List<java.awt.Menu> menus = menuRepository.findByMenuNameContainingIgnoreCase(keyword);

        menus.forEach(menu -> menuRepository.incrementSearchCount(menu.getMenuId())); // 검색 횟수 증가

        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}


