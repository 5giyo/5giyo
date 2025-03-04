package com.example.ogiyo.menus.controller;

import com.example.ogiyo.menus.dto.MenuRequest;
import com.example.ogiyo.menus.dto.MenuResponse;
import com.example.ogiyo.menus.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 메뉴 추가
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(@RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuService.createMenu(request));
    }

    // 특정 메뉴 조회
    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenu(menuId));
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

    // 메뉴 검색
    @GetMapping("/search")
    public ResponseEntity<List<MenuResponse>> searchMenus(@RequestParam String query) {
        return ResponseEntity.ok(menuService.searchMenus(query));
    }
}

