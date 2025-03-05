package com.example.ogiyo.menus.service;

import com.example.ogiyo.menus.repository.LikeCountRepository;
import jakarta.transaction.Transactional;
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

