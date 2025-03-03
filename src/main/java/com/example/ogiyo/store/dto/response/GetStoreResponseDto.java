package com.example.ogiyo.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetStoreResponseDto {
    private Long storeId;
    private String storeName;
    private String operatingHours;
    private String announcement;
    //    private String tip;
    private Long minPrice;
    private String imageUrl;
    private String status;
//    private List<Menu> menus;
}
