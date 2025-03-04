package com.example.ogiyo.domain.review.entity;

import com.example.ogiyo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;

@Entity
@Getter
@Table(name = "review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @Column(nullable = false)
    private Byte rating;

    @Column(nullable = false, length = 255)
    private String content;

    public Review() {}

    public Review(Store store, Member member, Order order, Byte rating, String content){
        this.store = store;
        this.member = member;
        this.order = order;
        this.rating = rating;
        this.content = content;
    }

    public void updateReview(Byte rating, String content){
        this.rating = rating;
        this.content = content;
    }
}
