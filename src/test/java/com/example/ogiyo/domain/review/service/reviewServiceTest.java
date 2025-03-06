package com.example.ogiyo.domain.review.service;

import com.example.ogiyo.common.s3.S3Manager;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.order.service.OrderServiceImpl;
import com.example.ogiyo.domain.photo.service.PhotoService;
import com.example.ogiyo.domain.review.repository.ReviewRepository;
import com.example.ogiyo.domain.store.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class reviewServiceTest {
    @InjectMocks
    ReviewService reviewService;
    @Mock // 의존성을 Mock으로 대체
    private ReviewRepository reviewRepository;
    @Mock
    private StoreService storeService;
    @Mock
    private MemberService memberService;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private PhotoService photoService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private S3Manager s3Manager;


}
