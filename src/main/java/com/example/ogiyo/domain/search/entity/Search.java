package com.example.ogiyo.domain.search.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Long likeCount;

    @Column(nullable = false)
    private Long orderCount;

    @Column(nullable = false)
    private Long tip;

    @Column(nullable = false)
    private Long minPrice;
}
