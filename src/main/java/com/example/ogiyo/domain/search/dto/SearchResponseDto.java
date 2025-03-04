package com.example.ogiyo.domain.search.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SearchResponseDto {
    private String storeName;
    private List<String> menuNames = new ArrayList<>();
    private Long likeCount;
    private Long orderCount;
    private Long tip;
    private Long minPrice;
}
