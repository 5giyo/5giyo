package com.example.ogiyo.domain.ceoReview.entity;

import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CeoReview {
    @Id
    private Long reviewId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "reviewId", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    private String content;

    public CeoReview(Review review, Member member, String content) {
        this.review = review;
        this.reviewId = review.getId();
        this.member = member;
        this.content = content;
    }
    public void updateCeoReview(String content) {
        this.content = content;
    }
}
