package com.example.ogiyo.store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateStoreResponseDto {
    private Long storeId;
    private String storeName;
    private String status;
}
