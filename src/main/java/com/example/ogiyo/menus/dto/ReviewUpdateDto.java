package com.example.ogiyo.menus.dto;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewUpdateDto {
    private String content; // 수정할 내용
    private Integer rating; // 수정할 별점
}
