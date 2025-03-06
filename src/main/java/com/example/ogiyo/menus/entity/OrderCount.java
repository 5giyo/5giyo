package com.example.ogiyo.menus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCountId;

    private Integer orderCount = 0;

    public void increaseOrderCount() {
        this.orderCount += 1;
    }
}

