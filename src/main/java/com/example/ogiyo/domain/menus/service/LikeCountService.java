package com.example.ogiyo.domain.menus.service;

import com.example.ogiyo.domain.menus.repository.LikeCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeCountService {

    private final LikeCountRepository likeCountRepository;

//    @Transactional
//    public void addLike(Long likeCountId) {
//        likeCountRepository.increaseLikeCount(likeCountId);
//    }
//
//    @Transactional
//    public void removeLike(Long likeCountId) {
//        likeCountRepository.decreaseLikeCount(likeCountId);
//    }
}

