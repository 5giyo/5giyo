package com.example.ogiyo.domain.search.controller;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.domain.search.service.SearchService;
import com.example.ogiyo.domain.search.dto.SearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResponseDto>> search(@RequestParam String name) {
        return ResponseEntity.ok(searchService.findAllByName(name));
    }

    @GetMapping("/popular-keyword")
    public ResponseEntity<ResponseDto<?>> popularKeyword() {

    }
}
