package com.example.ogiyo.domain.menus.service;

import com.example.ogiyo.domain.menus.dto.MenuResponse;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.menus.dto.MenuRequest;
import com.example.ogiyo.domain.menus.entity.LikeCount;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.entity.OrderCount;
import com.example.ogiyo.domain.menus.entity.SearchCount;
import com.example.ogiyo.domain.menus.enums.Status;
import com.example.ogiyo.domain.menus.repository.LikeCountRepository;
import com.example.ogiyo.domain.menus.repository.MenuRepository;
import com.example.ogiyo.domain.menus.repository.OrderCountRepository;
import com.example.ogiyo.domain.menus.repository.SearchCountRepository;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderCountRepository orderCountRepository;
    private final LikeCountRepository likeCountRepository;
    private final SearchCountRepository searchCountRepository;
    private final StoreRepository storeRepository;

    // 메뉴 추가
    @Transactional
    public Menu createMenu(Long storeId, String category, String menuName, Integer price, String option, Status status) {
        // 사장님 권한
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // JWT에서 사용자 username 불러오기

        // 로그인한 사용자가 판별
//        Store store = storeRepository.findById(storeId).
//                orElseThrow(()->new RuntimeException("가게를 찾을수 없습니다."));
//
//        if (!store.get)


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
    public List<MenuResponse> getMenusByStore(Long storeId, String category) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new RuntimeException("가게를 찾을수 없습니다."));
        // 메뉴를 검색 조건에 맞춰 필터링
        List<Menu> menus = menuRepository.findByStore_StoreIdAndCategory(storeId,category);
        return menus.stream()
                .map(menu -> new MenuResponse(menu))
                .collect(Collectors.toList());
    }
    // 메뉴 엔티티 전체 반환

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

