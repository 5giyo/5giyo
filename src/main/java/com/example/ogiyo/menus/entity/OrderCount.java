package com.example.ogiyo.menus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderCount")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderCountId;

    private Integer orderCount = 0; // 기본값 설정

    public void increaseOrderCount() {
        this.orderCount += 1;  //  주문 횟수 증가
    }
}
