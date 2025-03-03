package com.example.ogiyo.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateStoreRequestDto {
    private String storeName;
    private String operatingHours;
    private String announcement;
//    private String tip;
    private Long minPrice;
    private String imageUrl;
}
