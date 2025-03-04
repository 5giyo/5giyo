package com.example.ogiyo.order.entity;

import com.example.ogiyo.order.dto.request.UpdateOrderRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private String paymentMethod;
    private int totalPrice;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문상태

//    @OneToMany
//    private Menu menu;

    public Order() {

    }

    public Order(String paymentMethod, int totalPrice, OrderStatus orderStatus) {
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
    }

    public void update(String paymentMethod, OrderStatus orderStatus,int quantity) {
        this.paymentMethod = paymentMethod;
        this.orderStatus = orderStatus;
        this.quantity = quantity;


    }
}
