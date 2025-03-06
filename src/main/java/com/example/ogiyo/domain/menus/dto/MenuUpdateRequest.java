package com.example.ogiyo.domain.menus.dto;
import com.example.ogiyo.domain.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuUpdateRequest {
    private String category;
    private String menuName;
    private Integer price;
    private Status status;
    private String option;
}
