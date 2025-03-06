package com.example.ogiyo.domain.order.entity;

import com.example.ogiyo.common.entity.BaseEntity;
import com.example.ogiyo.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menus")
//    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members")
    private Member member;

    public Order() {

    }

    public Order(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void update(OrderStatus orderStatus,int quantity) {
        this.orderStatus = orderStatus;
        this.quantity = quantity;


    }
}
