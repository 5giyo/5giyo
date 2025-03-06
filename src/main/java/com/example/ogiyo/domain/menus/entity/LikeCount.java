package com.example.ogiyo.domain.menus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeCountId;

    private Integer likeCount = 0;

    public void increaseLikeCount() {
        this.likeCount ++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount --;
        }
    }
}

