package com.example.ogiyo.review.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.review.dto.request.SaveReviewRequestDto;
import com.example.ogiyo.review.dto.request.UpdateReviewRequestDto;
import com.example.ogiyo.review.dto.response.GetReviewResponseDto;
import com.example.ogiyo.review.dto.response.PagingReviewResponseDto;
import com.example.ogiyo.review.dto.response.SaveReviewResponseDto;
import com.example.ogiyo.review.entity.Review;
import com.example.ogiyo.review.repository.ReviewRepository;
import com.example.ogiyo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    private final MemberService memberService;
    private final OrderService orderService;

    public ResponseDto<SaveReviewResponseDto> saveReview(Long storeId, SaveReviewRequestDto reviewRequestDto) {
        Review review = new Review(
                storeService.getStore(storeId),
                memberService.findMember(1),
                orderService.findOrder(reviewRequestDto.getOrderId()),
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent()
        );
        Review savedReview = reviewRepository.save(review);
        return ResponseDto.success(buildSaveReviewResponseDto(savedReview));
    }

    private SaveReviewResponseDto buildSaveReviewResponseDto(Review review) {
        SaveReviewResponseDto response = new SaveReviewResponseDto(
                review.getMember().getName(),
                review.getRating(),
                review.getContent()
        );
        return response;
    }

    public ResponseDto<PagingReviewResponseDto> getReview(Long storeId, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Review> reviewPage = reviewRepository.findAllByStoreIdOrderByModifiedAtDesc(storeId, pageRequest);

        List<GetReviewResponseDto> reviews = reviewPage.getContent().stream()
                .map(review -> new GetReviewResponseDto(
                        review.getId(),
                        review.getMember().getId(),
                        review.getModifiedAt(),
                        review.getRating(),
                        review.getContent()
                )).toList();

        PagingReviewResponseDto response = new PagingReviewResponseDto(
                reviews,
                (int) reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.getNumber() + 1
        );
        return ResponseDto.success(response);
    }

    @Transactional
    public ResponseDto<SaveReviewResponseDto> updateReview(Long reviewId, UpdateReviewRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("수정하려는 리뷰를 찾을 수 없습니다."));

        review.updateReview(requestDto.getRating(), requestDto.getContent());

        return ResponseDto.success(buildSaveReviewResponseDto(review));
    }

    public ResponseDto<String> deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return ResponseDto.success("리뷰를 삭제하였습니다.");
    }
}
