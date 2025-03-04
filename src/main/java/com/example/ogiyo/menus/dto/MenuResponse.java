package com.example.ogiyo.menus.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String storeName;
    private String category;
    private String menuName;
    private int price;
    private int searchCount;
}
