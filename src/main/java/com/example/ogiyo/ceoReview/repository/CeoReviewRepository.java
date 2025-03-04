package com.example.ogiyo.ceoReview.repository;

import com.example.ogiyo.ceoReview.entity.CeoReview;
import com.example.ogiyo.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CeoReviewRepository extends JpaRepository<CeoReview, Long> {
}
