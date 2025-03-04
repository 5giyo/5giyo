package com.example.ogiyo.ceoReview.entity;

import com.example.ogiyo.common.BaseEntity;
import com.example.ogiyo.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class CeoReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    private String content;
}
