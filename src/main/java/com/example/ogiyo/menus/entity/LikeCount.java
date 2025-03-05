package com.example.ogiyo.menus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LikeCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeCountId;

    private Integer likeCount = 0; // 기본값 설정

    //현재 좋아요 개수 반환
    public Integer getCount() {
        return likeCount;
    }

    public void increaseLikeCount() {
        this.likeCount += 1;  // ✅ 좋아요 증가
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;  // ✅ 좋아요 감소 (0 이하 방지)
        }

    }
}

