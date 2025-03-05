package com.example.ogiyo.domain.cart.entity;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RedisHash("cart")
public class Cart implements Serializable {

    @Id
    private Long memberId;
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Long menuId, int quantity) {
        CartItem existingItem = items.stream()
                .filter(cartItem->cartItem.getMenuId().equals(menuId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.add(new CartItem(menuId, quantity));
        }
    }
}
