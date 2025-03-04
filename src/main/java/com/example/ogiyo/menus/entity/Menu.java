package com.example.ogiyo.menus.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId; // 리뷰 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 리뷰 작성자 (FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // 가게 (FK)

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order; // 주문 (1:1 관계, 한 주문당 하나의 리뷰만 작성 가능)

    @Column(nullable = false)
    private Integer rating; // 별점 (1~5점)

    @Column(columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    @Column(nullable = false)
    private Boolean isOwnerReplied; // 사장님이 답변했는지 여부

    private LocalDateTime createdAt; // 리뷰 작성일
    private LocalDateTime updatedAt; // 리뷰 수정일

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isOwnerReplied = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateReview(String content, int rating) {
        this.content = content;
        this.rating = rating;
        this.updatedAt = LocalDateTime.now();
    }

    public void setOwnerReplied() {
        this.isOwnerReplied = true;
    }
}


