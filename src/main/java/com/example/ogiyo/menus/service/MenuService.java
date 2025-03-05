package com.example.ogiyo.menus.service;

import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.menus.dto.MenuRequest;
import com.example.ogiyo.menus.dto.MenuResponse;
import com.example.ogiyo.menus.entity.LikeCount;
import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.entity.OrderCount;
import com.example.ogiyo.menus.entity.SearchCount;
import com.example.ogiyo.menus.enums.Status;
import com.example.ogiyo.menus.repository.LikeCountRepository;
import com.example.ogiyo.menus.repository.MenuRepository;
import com.example.ogiyo.menus.repository.OrderCountRepository;
import com.example.ogiyo.menus.repository.SearchCountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderCountRepository orderCountRepository;
    private final LikeCountRepository likeCountRepository;
    private final SearchCountRepository searchCountRepository;

    // 메뉴 추가
    @Transactional
    public Menu createMenu(Long storeId, String category, String menuName, Integer price, String option, Status status) {
        OrderCount orderCount = orderCountRepository.save(new OrderCount());
        LikeCount likeCount = likeCountRepository.save(new LikeCount());
        SearchCount searchCount = searchCountRepository.save(new SearchCount());

        Menu menu = Menu.builder()
                .store(Store.builder().storeId(storeId).build())  // 가게 ID 설정
                .category(category)
                .menuName(menuName)
                .price(price)
                .status(status)
                .option(option)
                .orderCount(orderCount)
                .likeCount(likeCount)
                .searchCount(searchCount)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();

        return menuRepository.save(menu);
    }

    // 특정 메뉴 조회
    public Menu getMenu(Long menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
    }

    // 주문횟수, 좋아요(찜)
    @Transactional
    public void increaseOrderCount(Long menuId) {
        Menu menu = getMenu(menuId);
        menu.getOrderCount().increaseOrderCount();
    }
    @Transactional
    public void increaseLikeCount(Long menuId) {
        Menu menu = getMenu(menuId);
        menu.getLikeCount().increaseLikeCount();
    }
    @Transactional
    public void decreaseLikeCount(Long menuId) {
        Menu menu = getMenu(menuId);
        menu.getLikeCount().decreaseLikeCount();
    }
    @Transactional
    public void increaseSearchCount(Long menuId) {
        Menu menu = getMenu(menuId);
        menu.getSearchCount().increaseSearchCount();
    }

    // 가게별 메뉴 조회
    public List<MenuResponse> getMenusByStore(Long storeId) {
        List<Menu> menus = menuRepository.findByStore_StoreId(storeId);
        return menus.stream()
                .map(menu -> new MenuResponse(menu.getMenuId(), menu.getStore().getStoreName(), menu.getCategory(),
                        menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount()))
                .collect(Collectors.toList());
    }

    // 메뉴 수정
    @Transactional
    public Menu updateMenu(Long menuId, MenuRequest dto) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다. id=" + menuId));

        menu.updateMenu(
                dto.getCategory(),
                dto.getMenuName(),
                dto.getPrice(),
                dto.getStatus(),
                dto.getOption()
        );

        return menu;
    }

    // 메뉴 삭제
    public void deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new RuntimeException("메뉴를 찾을 수 없습니다.");
        }
        menuRepository.deleteById(menuId);
    }



//    // 메뉴 검색 (이름 기준)
//    public List<MenuResponse> searchMenus(String keyword) {
//        List<Menu> menus = menuRepository.findByMenuNameContainingIgnoreCase(keyword);
//        menus.forEach(menu -> {
//            menu.setSearchCount(menu.getSearchCount() + 1);
//            menuRepository.save(menu);
//        });
//
//        return menus.stream()
//                .map(menu -> new MenuResponse(menu.getMenuId(), menu.getStore().getName(), menu.getCategory(),
//                        menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount()))
//                .collect(Collectors.toList());
//    }
}

