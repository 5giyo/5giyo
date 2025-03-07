package com.example.ogiyo.domain.cart.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCartRequestDto {
    private Long menuId;
    private int quantity;

    public UpdateCartRequestDto(Long menuId, int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
