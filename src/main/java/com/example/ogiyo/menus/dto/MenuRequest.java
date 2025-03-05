package com.example.ogiyo.menus.dto;

import com.example.ogiyo.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private Long storeId;
    private String category;
    private String menuName;
    private Integer price;
    private Status status;
    private String option;
}

