package com.example.ogiyo.menus.entity;


import com.example.ogiyo.menus.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "storeId", nullable = false)
//    private Store store;

    private String category;
    private String menuName;
    private Integer price;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer searchCount;

    private String option; // 현재는 단순 문자열 (추후 변경 가능)

//    public DoubleSummaryStatistics getSearchCountEntity() {
//        return
//    }
    // 오더 카운트
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "orderCountId", referencedColumnName = "orderCountId")
    private OrderCount orderCount;
    // 좋아요(찜) 카운트
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "likeCountId", referencedColumnName = "likeCountId")
    private LikeCount likeCount;

    // LikeCount 메서드는 LikeCount가 null일 경우 0을 반환해서 NPE방지
    public Integer getLikeCount() {
        return likeCount != null ? likeCount.getCount() : 0;
    }
    
    //  검색횟우 카운트
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "searchCountId", referencedColumnName = "searchCountId")
    private SearchCount searchCountEntity;


    public void setCategory(String category) {

    }

    public void setMenuName(String menuName) {
    }

    public void setPrice(Integer price) {
    }

    public void setStatus(Status status) {
    }

    public void setOption(String option) {
    }

    public void setModifiedAt(LocalDateTime now) {
    }
}



