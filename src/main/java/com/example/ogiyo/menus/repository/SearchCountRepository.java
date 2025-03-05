package com.example.ogiyo.menus.repository;

import com.example.ogiyo.menus.entity.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCountRepository extends JpaRepository<SearchCount, Long> {
}

