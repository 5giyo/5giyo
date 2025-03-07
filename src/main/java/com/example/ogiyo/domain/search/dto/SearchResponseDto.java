package com.example.ogiyo.domain.search.dto;

import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.store.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SearchResponseDto {
    private String storeName;
    private List<String> menus;
    private Long likeCount;
    private Long orderCount;
    private Long tip;
    private Long minPrice;

    public SearchResponseDto(Store store) {
        this.storeName = store.getStoreName();
        this.menus = store.getMenus().stream().map(Menu::getMenuName).toList();
        this.likeCount = 0L;
        this.orderCount = 0L;
        this.tip = 0L;
        this.minPrice = store.getMinPrice();
    }
}
