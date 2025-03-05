package com.example.ogiyo.menus.repository;

import com.example.ogiyo.menus.entity.LikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeCountRepository extends JpaRepository<LikeCount, Long> {
    Optional<LikeCount> findById(Long likeCountId);  // ✅ 특정 좋아요 카운트 조회

    @Modifying
    @Query("UPDATE LikeCount l SET l.likeCount = l.likeCount + 1 WHERE l.likeCountId = :likeCountId")
    void increaseLikeCount(@Param("likeCountId") Long likeCountId);  // ✅ 좋아요 증가

    @Modifying
    @Query("UPDATE LikeCount l SET l.likeCount = l.likeCount - 1 WHERE l.likeCountId = :likeCountId AND l.likeCount > 0")
    void decreaseLikeCount(@Param("likeCountId") Long likeCountId);
}
