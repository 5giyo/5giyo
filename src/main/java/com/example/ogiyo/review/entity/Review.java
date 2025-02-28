package com.example.ogiyo.reviews.entity;

import com.example.ogiyo.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")
public class Reviews extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storesId", nullable = false)
    private Stores stores;  // 가게 ID (외래키)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;  // 회원 ID (외래키)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menusId")
    private Menus menus;  // 메뉴 ID (NULL 가능)

    @Column(nullable = false)
    private byte rating;  // 별점

    @Column(nullable = false, length = 255)
    private String content;  // 내용


}
