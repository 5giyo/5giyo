package com.example.ogiyo.domain.cart.entity;


import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@RedisHash("cart")
public class Cart implements Serializable {

    @Id
    private final Long memberId;
    private final Map<Long, CartItem> items;

    public Cart(Long memberId) {
        this.memberId = memberId;
        this.items = new HashMap<>();
    }

    public void addItem(CartItem item) {
        items.put(item.getMenuId(), item);
    }

    public void removeItem(Long menuId) {
        items.remove(menuId);
    }


    public int getTotalQuantity() {
        return items.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void updateItemQuantity(Long menuId, int quantity) {
        CartItem item = items.get(menuId);
        if (item != null) {
            if (quantity > 0) {
                item.setQuantity(quantity);
            } else {
                // 수량이 0 이하면 아이템 제거
                items.remove(menuId);
            }
        }
    }
}
