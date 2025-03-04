package com.example.ogiyo.domain.search.service;

import com.example.ogiyo.domain.search.dto.SearchResponseDto;
import com.example.ogiyo.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final StoreRepository storeRepository;

    public List<SearchResponseDto> findAllByName(String name) {
//        storeRepository.findAllByName(name);
        return List.of();
    }
}
