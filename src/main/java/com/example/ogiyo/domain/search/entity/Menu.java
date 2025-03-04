package com.example.ogiyo.domain.search.entity;

import com.example.ogiyo.domain.store.entity.Store;
import jakarta.persistence.*;

@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    Store store;
}
