package com.example.ogiyo.menus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Menu {


    private Long menuId; // PK


    private Store store; // FK (가게)


    private String category; // 한식, 양식 등


    private String menuName; // 메뉴명


    private int price; // 가격


    private LocalDateTime createdAt = LocalDateTime.now();


    private LocalDateTime modifiedAt = LocalDateTime.now();


    private boolean status; // true(판매중), false(판매중지)


    private int searchCount; // 조회수


}

