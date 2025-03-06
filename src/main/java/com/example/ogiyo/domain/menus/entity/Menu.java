package com.example.ogiyo.domain.menus.entity;


import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.menus.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String option; // 추후 확장 가능

    @OneToOne
    @JoinColumn(name = "order_count_id")
    private OrderCount orderCount;

    @OneToOne
    @JoinColumn(name = "like_count_id")
    private LikeCount likeCount;

    @OneToOne
    @JoinColumn(name = "search_count_id")
    private SearchCount searchCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    // 생성 메서드(createMenu)
    public static Menu menu(Store store, String category, String menuName, Integer price, Status status,
                                  String option, OrderCount orderCount, LikeCount likeCount, SearchCount searchCount) {
        return Menu.builder()
                .store(store)
                .category(category)
                .menuName(menuName)
                .price(price)
                .status(status)
                .option(option)
                .orderCount(orderCount)
                .likeCount(likeCount)
                .searchCount(searchCount)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }

    // 비즈니스 로직
    public void updateMenu(String category, String menuName, Integer price, Status status, String option) {
        this.category = category;
        this.menuName = menuName;
        this.price = price;
        this.status = status;
        this.option = option;
        this.modifiedAt = LocalDateTime.now();
    }
}



