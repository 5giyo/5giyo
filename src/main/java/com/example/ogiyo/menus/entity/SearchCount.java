package com.example.ogiyo.menus.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "search_count")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SearchCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchCountId;

    private Integer searchCount = 0;

    public void increaseSearchCount() {
        this.searchCount += 1;  // ✅ 검색 횟수 증가
    }
}

