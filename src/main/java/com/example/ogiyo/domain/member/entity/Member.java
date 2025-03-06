package com.example.ogiyo.domain.member.entity;

import com.example.ogiyo.auth.enums.MemberRole;
import com.example.ogiyo.common.entity.BaseEntity;
import jakarta.persistence.*;
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
    private String name;
    private String email;
    private String password;
    private MemberRole role;
    private int countOwnedStore = 0;

    private boolean deleted = false;

    public void updateName(String name) {this.name = name;}
    public void updateEmail(String email) {this.email = email;}
    public void updatePassword(String password) {this.password = password;}

    public void delete() {this.deleted =  true;}

    public void addCountOwnedStore() {this.countOwnedStore++;}
    public void removeCountOwnedStore() {this.countOwnedStore--;}

    @Builder
    public Member(String name, String email, String password, MemberRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
