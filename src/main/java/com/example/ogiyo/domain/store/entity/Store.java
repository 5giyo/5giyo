package com.example.ogiyo.domain.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

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

//    @OneToOne
//    User user;

//    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    List<Menu> menus = new ArrayList<>();


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

    public enum Status {
        OPEN, CLOSED, PERMANENTLY_CLOSED
    }
}
