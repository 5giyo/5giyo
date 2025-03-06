package com.example.ogiyo.domain.order.entity;

import com.example.ogiyo.common.entity.BaseEntity;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.menus.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String paymentMethod;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menus")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public Order() {

    }

    public void updateOrder(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void update(OrderStatus orderStatus,String paymentMethod) {
        this.orderStatus = orderStatus;
        this.paymentMethod = paymentMethod;
    }
}
