package com.example.ogiyo.domain.search.repository;

import com.example.ogiyo.domain.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
//    @Query("SELECT s.storeName, s.minPrice, m.menuName FROM Store s JOIN FETCH Menu m ON s.storeId = m.storeId WHERE s.storeName LIKE %:name% OR m.menuName LIKE %:name%")
//    List<Search> findAllByName(@Param("name") String name);
}
