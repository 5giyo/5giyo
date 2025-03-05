package com.example.ogiyo.menus.dto;

import com.example.ogiyo.menus.entity.Menu;
import com.example.ogiyo.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private String option;
    private Long menuId;
    private String storeName;
    private String category;
    private String menuName;
    private int price;
    private Status status;
    private LocalDateTime modifiedAt;

    public MenuResponse(Menu menu) {
        this.menuId = menu.getMenuId();
        this.category = menu.getCategory();
        this.menuName = menu.getMenuName();
        this.price = menu.getPrice();
        this.status = menu.getStatus();
        this.option = menu.getOption();
        this.modifiedAt = menu.getModifiedAt();
    }

}
