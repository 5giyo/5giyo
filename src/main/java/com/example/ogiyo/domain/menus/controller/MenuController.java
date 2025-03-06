package com.example.ogiyo.domain.menus.controller;
import com.example.ogiyo.domain.menus.dto.CreateMenuResponseDto;
import com.example.ogiyo.domain.menus.dto.MenuRequest;
import com.example.ogiyo.domain.menus.dto.MenuResponse;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CreateMenuResponseDto> createMenu(@RequestHeader("Authorization") String token, @RequestBody MenuRequest request) {
        CreateMenuResponseDto responseDto = menuService.createMenu(
                token,
                request.getStoreId(),
                request.getCategory(),
                request.getMenuName(),
                request.getPrice(),
                request.getMenuOption(),
                request.getStatus()
        );
        return ResponseEntity.ok(responseDto);
    }

    // 메뉴 검색 (단독 조회 x, 가게 조회시 함께 조회)
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<MenuResponse>> getMenuByStore(
            @PathVariable Long storeId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {

        if (includeDeleted) {
            return ResponseEntity.ok(menuService.getMenusByHistory(storeId)); // 삭제된 메뉴 제외한 전체
        } else {
            return ResponseEntity.ok(menuService.getMenusByStore(storeId, category)); // category가 있을 경우 필터링
        }
    }

    // 전체 메뉴 엔티티 조회(팀원 요청)
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getMenuEntity() {
        return ResponseEntity.ok(menuService.getMenuEntity());
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



    // 메뉴 수정(사장님만)
    @PutMapping("/{menuId}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long menuId, @RequestBody MenuRequest request) {
        return ResponseEntity.ok(menuService.updateMenu(menuId, request));
    }

    // 메뉴 삭제(본인가게 메뉴만, 메뉴의 상태만 삭제상태)
    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(
            @PathVariable Long menuId,
            @RequestParam Long ownerId
    ) {
        try {
            menuService.deleteMenu(menuId, ownerId);
            return ResponseEntity.ok("메뉴가 삭제 상태로 변경되었습니다.");
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

