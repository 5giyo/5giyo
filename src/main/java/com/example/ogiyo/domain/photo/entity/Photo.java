package com.example.ogiyo.domain.photo.entity;

import com.example.ogiyo.common.entity.BaseEntity;
import com.example.ogiyo.domain.photo.domainType.DomainType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "photo",
        indexes = {
                @Index(name = "idx_type_key", columnList = "domainType, domainKey")
        })
public class Photo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainType domainType;

    @Column(nullable = false)
    private Long domainKey;

    @Column(nullable = false)
    private int imageSeq; //이미지 개수

    @Column(nullable = false)
    private String photoKeyName;

    @Column(nullable = false)
    private String photoUrl;

    public Photo(DomainType domainType, Long domainKey, Integer imageSeq, String photoKeyName, String photoUrl) {
        this.domainType = domainType;
        this.domainKey = domainKey;
        this.imageSeq = imageSeq;
        this.photoKeyName = photoKeyName;
        this.photoUrl = photoUrl;
    }

}
