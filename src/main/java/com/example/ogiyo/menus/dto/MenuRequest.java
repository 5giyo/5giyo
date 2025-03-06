package com.example.ogiyo.menus.dto;

import com.example.ogiyo.menus.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private Long storeId;
    private String category;
    private String menuName;
    private int price;
    private Status status;

    public String getOption() {
        return null;
    }
}
