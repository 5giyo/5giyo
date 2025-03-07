package com.example.ogiyo.domain.cart.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
@Getter
@RedisHash("cart")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart implements Serializable {

    @Id
    private Long memberId;
    private Map<Long, Integer> items; // menuId를 키로, 수량을 값으로 저장

    public Cart(Long memberId) {
        this.memberId = memberId;
        this.items = new HashMap<>();
    }

    public void addItem(Long menuId, int quantity) {
        items.put(menuId, items.getOrDefault(menuId, 0) + quantity);
    }

    public void removeItem(Long menuId) {
        items.remove(menuId);
    }

    @JsonProperty("totalQuantity")
    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public void updateItemQuantity(Long menuId, int quantity) {
        if (quantity > 0) {
            items.put(menuId, quantity);
        } else {
            items.remove(menuId);
        }
    }

    public int getItemQuantity(Long menuId) {
        return items.getOrDefault(menuId, 0);
    }
}