package com.example.ogiyo.domain.photo.repository;

import com.example.ogiyo.domain.photo.domainType.DomainType;
import com.example.ogiyo.domain.photo.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT COALESCE(MAX(p.seq), 0) FROM Photo p WHERE p.domainKey = :domainKey")
    int findMaxSeqByDomainKey(@Param("domainKey") Long domainKey);

    void deleteByDomainTypeAndDomainKey(DomainType domainType, Long domainKey);

    List<Photo> findByDomainTypeAndDomainKey(DomainType domainType, Long domainKey);

}
