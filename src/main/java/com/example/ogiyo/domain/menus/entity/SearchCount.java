package com.example.ogiyo.domain.menus.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long searchCountId;

    private int searchCount = 0;

    public void increaseSearchCount() {
        this.searchCount ++;
    }
    public int getSearchCount() {
        int count = this.searchCount;
    }
}

