package com.example.ogiyo.domain.menus.dto;

import com.example.ogiyo.domain.menus.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CreateMenuResponseDto {
    private Long menuId;
    private Long storeId;
    private String category;
    private String menuName;
    private Integer price;
    private String menuOption;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


}
