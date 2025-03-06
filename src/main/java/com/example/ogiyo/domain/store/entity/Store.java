package com.example.ogiyo.domain.store.entity;

import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.menus.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    private String operatingHours;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String announcement;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long minPrice;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    Member owner;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    List<Menu> menus = new ArrayList<>();

    public void updateStore(String storeName, String operatingHours, String announcement, Long minPrice, String imageUrl) {
        this.storeName = storeName;
        this.operatingHours = operatingHours;
        this.announcement = announcement;
        this.minPrice = minPrice;
        this.imageUrl = imageUrl;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void addMenu(Menu menu) {
        if (!menus.contains(menu)) {
            this.menus.add(menu);
        }
    }

    public void removeMenu(Menu menu) {
        if (menus.contains(menu)) {
            this.menus.remove(menu);
        }
    }

    public enum Status {
        OPEN, CLOSED, PERMANENTLY_CLOSED
    }
}
