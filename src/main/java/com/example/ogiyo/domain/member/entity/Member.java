package com.example.ogiyo.domain.member.entity;

import com.example.ogiyo.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@SQLRestriction("deleted = false")
@Getter
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    private boolean deleted = false;

    public void updateEmail(String email) {this.email = email;}

    public void updatePassword(String password) {this.password = password;}

    public void delete() {this.deleted =  true;}

    @Builder
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
