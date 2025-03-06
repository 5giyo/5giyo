package com.example.ogiyo.domain.cart.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class CartItem implements Serializable {
    private final Long menuId;
    @Setter
    private int quantity;

    public CartItem(Long menuId, int quantity ) {
        this.menuId = menuId;
        this.quantity = quantity;
    }
}

