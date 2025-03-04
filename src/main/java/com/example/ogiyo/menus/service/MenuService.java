package com.example.ogiyo.menus.service;

import com.example.ogiyo.menus.dto.MenuRequest;
import com.example.ogiyo.menus.dto.MenuResponse;
import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;


    // 메뉴 추가
    public MenuResponse createMenu(MenuRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        Menu menu = new Menu();
        menu.setStore(store);
        menu.setCategory(request.getCategory());
        menu.setMenuName(request.getMenuName());
        menu.setPrice(request.getPrice());
        menu.setStatus(request.getStatus());
        menuRepository.save(menus);

        return new MenuResponse(menu.getMenuId(), store.getName(), menu.getCategory(),
                menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount());
    }

    // 특정 메뉴 조회
    public MenuResponse getMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        return new MenuResponse(menu.getMenuId(), menu.getStore().getName(), menu.getCategory(),
                menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount());
    }

    // 가게별 메뉴 조회
    public List<MenuResponse> getMenusByStore(Long storeId) {
        List<Menu> menus = menuRepository.findByStore_StoreId(storeId);
        return menus.stream()
                .map(menu -> new MenuResponse(menu.getMenuId(), menu.getStore().getName(), menu.getCategory(),
                        menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount()))
                .collect(Collectors.toList());
    }

    // 메뉴 수정
    public MenuResponse updateMenu(Long menuId, MenuRequest request) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        menu.setCategory(request.getCategory());
        menu.setMenuName(request.getMenuName());
        menu.setPrice(request.getPrice());
        menu.setStatus(request.getStatus());
        menuRepository.save(menu);

        return new MenuResponse(menu.getMenuId(), menu.getStore().getName(), menu.getCategory(),
                menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount());
    }

    // 메뉴 삭제
    public void deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new RuntimeException("메뉴를 찾을 수 없습니다.");
        }
        menuRepository.deleteById(menuId);
    }

    // 메뉴 검색 (이름 기준)
    public List<MenuResponse> searchMenus(String keyword) {
        List<Menu> menus = menuRepository.findByMenuNameContainingIgnoreCase(keyword);
        menus.forEach(menu -> {
            menu.setSearchCount(menu.getSearchCount() + 1);
            menuRepository.save(menu);
        });

        return menus.stream()
                .map(menu -> new MenuResponse(menu.getMenuId(), menu.getStore().getName(), menu.getCategory(),
                        menu.getMenuName(), menu.getPrice(), menu.getStatus(), menu.getSearchCount()))
                .collect(Collectors.toList());
    }
}

