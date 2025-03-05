package com.example.ogiyo.domain.cart.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CartItem implements Serializable {
    private Long menuId;
    private int quantity;

    public CartItem(Long menuId, int quantity) {
        this.menuId = menuId;
        this.quantity = quantity;
    }
}

