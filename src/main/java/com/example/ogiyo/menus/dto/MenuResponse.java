package com.example.ogiyo.menus.dto;

import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String storeName;
    private String category;
    private String menuName;
    private Integer price;
    private Status status;
    private String option;
    private Integer searchCount;
    private Integer orderCount;
    private Integer likeCount;

    public MenuResponse(Menu menu) {
        this.menuId = menu.getMenuId();
//        this.storeName = menu.getStore().getName();
        this.category = menu.getCategory();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.option = menu.getOption();
        this.searchCount = Math.toIntExact(menu.getSearchCountEntity().getCount());
        this.orderCount = menu.getOrderCount().getCount();
        this.likeCount = menu.getLikeCount();
    }

    public MenuResponse(java.awt.Menu menu) {
    }
}
