package com.example.ogiyo.menus.dto;
import com.example.ogiyo.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
