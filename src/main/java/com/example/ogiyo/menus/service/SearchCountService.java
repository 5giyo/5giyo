package com.example.ogiyo.menus.service;
import com.example.ogiyo.menus.repository.SearchCountRepository;
import jakarta.transaction.Transactional;
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

