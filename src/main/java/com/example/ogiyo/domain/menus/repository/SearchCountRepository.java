package com.example.ogiyo.domain.menus.repository;
import com.example.ogiyo.domain.menus.entity.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface SearchCountRepository extends JpaRepository<SearchCount, Long> {
    @Modifying
    @Query("UPDATE SearchCount s SET s.searchCount = s.searchCount + 1 WHERE s.searchCountId = :searchCountId")
    void increaseSearchCount(@Param("searchCountId") Long searchCountId);
}

