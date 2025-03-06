package com.example.ogiyo.domain.review.entity;

import com.example.ogiyo.common.entity.BaseEntity;
import com.example.ogiyo.domain.ceoReview.entity.CeoReview;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @OneToOne(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private CeoReview ceoReview;

    @Column(nullable = false)
    private Byte rating;

    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String content;

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
