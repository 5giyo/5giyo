package com.example.ogiyo.menus.controller;

import com.example.ogiyo.menus.dto.MenuRequest;
import com.example.ogiyo.menus.dto.MenuResponse;
import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 메뉴 생성
    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody MenuRequest request) {
        com.example.ogiyo.menus.entity.Menu menu = menuService.createMenu(request.getStoreId(), request.getCategory(), request.getMenuName(),
                request.getPrice(), request.getOption(), request.getStatus());
        return ResponseEntity.ok(menu);
    }

    // 메뉴 검색
    @GetMapping("/{menuId}")
    public ResponseEntity<com.example.ogiyo.menus.entity.Menu> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
    }

    // 주문횟수 증가
    @PostMapping("/{menuId}/order")
    public ResponseEntity<String> increaseOrderCount(@PathVariable Long menuId) {
        menuService.increaseOrderCount(menuId);
        return ResponseEntity.ok("주문 횟수가 증가되었습니다.");
    }

    // 좋아요 추가
    @PostMapping("/{menuId}/like")
    public ResponseEntity<String> addLike(@PathVariable Long menuId) {
        menuService.increaseLikeCount(menuId);
        return ResponseEntity.ok("좋아요가 추가되었습니다.");
    }

    // 좋아요 취소
    @PostMapping("/{menuId}/unlike")
    public ResponseEntity<String> removeLike(@PathVariable Long menuId) {
        menuService.decreaseLikeCount(menuId);
        return ResponseEntity.ok("좋아요가 취소되었습니다.");
    }

    // 검색횟수 증가(검증 코드)
    @PostMapping("/{menuId}/search")
    public ResponseEntity<String> increaseSearchCount(@PathVariable Long menuId) {
        menuService.increaseSearchCount(menuId);
        return ResponseEntity.ok("검색 횟수가 증가되었습니다.");
    }


    // 특정 가게의 메뉴 목록 조회
    @GetMapping("/stores/{storeId}")
    public ResponseEntity<List<MenuResponse>> getMenusByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(menuService.getMenusByStore(storeId));
    }

    // 메뉴 수정
    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(@PathVariable Long menuId, @RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, request));
    }

    // 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.noContent().build();
    }

}

