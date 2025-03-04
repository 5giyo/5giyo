package com.example.ogiyo.domain.store.dto.response;

import com.example.ogiyo.domain.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetStoresResponseDto {
    private Long storeId;
    private String storeName;
    //    private String tip;
    private Long minPrice;
    private String imageUrl;
    private String status;

    public GetStoresResponseDto(Store store) {
        this.storeId = store.getStoreId();
        this.storeName = store.getStoreName();
        this.minPrice = store.getMinPrice();
        this.imageUrl = store.getImageUrl();
        this.status = store.getStatus().toString();
    }
}
