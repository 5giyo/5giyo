package com.example.ogiyo.menus.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;  // PK

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;  // FK

    @Column(nullable = false)
    private String category; // 카테고리 (한식, 양식 등)

    @Column(nullable = false)
    private String menuName; // 메뉴명

    @Column(nullable = false)
    private int price; // 가격

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 날짜

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now(); // 수정 날짜

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status; // 상태 (판매중, 품절 등)

    @Column(nullable = false)
    private int searchCount = 0; // 검색 횟수

    @PreUpdate
    public void updateTimestamp() {
        this.modifiedAt = LocalDateTime.now();
    }
}


