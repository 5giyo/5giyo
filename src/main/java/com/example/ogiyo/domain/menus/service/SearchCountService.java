package com.example.ogiyo.domain.menus.service;
import com.example.ogiyo.domain.menus.repository.SearchCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class SearchCountService {

    private final SearchCountRepository searchCountRepository;

//    @Transactional
//    public void increaseSearchCount(Long searchCountId) {
//        searchCountRepository.increaseSearchCount(searchCountId);
//    }
}

