package com.example.ogiyo.domain.review.repository;

import com.example.ogiyo.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByStoreIdOrderByModifiedAtDesc(Long storeId, Pageable pageable);
}
