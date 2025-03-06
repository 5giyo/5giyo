package com.example.ogiyo.domain.menus.service;

import com.example.ogiyo.domain.menus.dto.MenuResponse;
import com.example.ogiyo.domain.order.dto.response.GetOrderResponseDto;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.repository.OrderRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final OrderCountRepository orderCountRepository;
    private final LikeCountRepository likeCountRepository;
    private final SearchCountRepository searchCountRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    // 메뉴 추가
    @Transactional
    public Menu createMenu(Long storeId, String category, String menuName, Integer price, String option, Status status) {
        // 사장님 권한
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // JWT에서 사용자 username 불러오기

         // 로그인한 사용자가 판별
        Store store = storeRepository.findById(storeId).
                orElseThrow(()->new RuntimeException("가게를 찾을수 없습니다."));

        if (!store.getOwner().getName().equals(currentUserName)) {
            throw new RuntimeException("권한이 없습니다. 사장님만 메뉴 생성이 가능합니다.");
        }
        // 권한 체크 이후 메뉴 생성
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

    // 메뉴 ID로만 메뉴 조회
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

    // 메뉴 조회(단독 조회 불가 가게 조회시 함께 조회) + 필터링 카테고리 추가
    public List<MenuResponse> getMenusByStore(Long storeId, String category) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new RuntimeException("가게를 찾을수 없습니다."));
        // 메뉴를 검색 조건에 맞춰 필터링
        List<Menu> menus = menuRepository.findByStore_StoreIdAndCategory(storeId,category);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }

    // 메뉴 조회(삭제된 메뉴 제외)
    public List<MenuResponse> getMenusByHistory(Long storeId) {
        List<Menu> menus = menuRepository.findByStore_StoreIdAndStatusNot(storeId, Status.DELETED);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }



    // 메뉴 엔티티 전체 반환(팀원 요청)
    @Transactional
    public List<MenuResponse> getMenuEntity() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(MenuResponse::new)
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

    // 메뉴 삭제(본인가게만, 삭제시 메뉴 상태만 삭제상태로 변경)
    // 가게 메뉴 조회 시 삭제된 메뉴 나타나지 않음, 주문 내역 조회시에는 삭제된 메뉴 정보 나타남
    public void deleteMenu(Long menuId, Long ownerId) {
        Menu menu = menuRepository.findWithStoreAndOwnerById(menuId)
                .orElseThrow(()-> new IllegalArgumentException("해당 메뉴 존재하지 않습니다."));

        if(menu.getStore().getOwner().getId().equals(ownerId)) {
            throw new SecurityException("해당 메뉴를 삭제할 권한이 없습니다.");
        }

        menu.setStatus(Status.DELETED);
    }


}

