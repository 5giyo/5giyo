package com.example.ogiyo.domain.ceoReview.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.ceoReview.dto.request.SaveCeoReviewRequestDto;
import com.example.ogiyo.domain.ceoReview.dto.request.UpdateCeoReviewRequestDto;
import com.example.ogiyo.domain.ceoReview.dto.response.SaveCeoReviewResponseDto;
import com.example.ogiyo.domain.ceoReview.dto.response.UpdateCeoReviewResponseDto;
import com.example.ogiyo.domain.ceoReview.entity.CeoReview;
import com.example.ogiyo.domain.ceoReview.repository.CeoReviewRepository;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.review.entity.Review;
import com.example.ogiyo.domain.review.service.ReviewService;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.service.StoreService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CeoReviewService {
    private final CeoReviewRepository ceoReviewRepository;
    private final ReviewService reviewService;
    private final StoreService storeService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public ResponseDto<SaveCeoReviewResponseDto> saveCeoReview(Long reviewId, String token, SaveCeoReviewRequestDto requestDto) {
        Review review = reviewService.getReviewById(reviewId);

        Store store = storeService.findStore(review.getStore().getId());
        Long memberId = jwtUtil.extractMemberId(token);

        if(!store.getOwner().getId().equals(memberId)){
            throw new InvalidRequestStateException("해당 가게의 사장이 아닙니다.");
        }
        Member member = memberService.findById(memberId).orElseThrow(()-> new EntityNotFoundException("회원을 찾을 수 없습니다."));

        CeoReview ceoReview = new CeoReview(review, member, requestDto.getContent());
        CeoReview savedCeoReview = ceoReviewRepository.save(ceoReview);

        SaveCeoReviewResponseDto response = new SaveCeoReviewResponseDto(
                savedCeoReview.getContent()
        );
        return ResponseDto.success(response);
    }

    @Transactional
    public ResponseDto<UpdateCeoReviewResponseDto> updateCeoReview(Long reviewId, String token, UpdateCeoReviewRequestDto requestDto) {
        CeoReview ceoReview = ceoReviewRepository.findById(reviewId).orElseThrow(()-> new EntityNotFoundException("수정하려는 답글이 없습니다."));
        Long memberId = jwtUtil.extractMemberId(token);

        if(!ceoReview.getMember().getId().equals(memberId)){
            throw new InvalidRequestStateException("가게 사장님에게만 수정 권한이 있습니다.");
        }
        ceoReview.updateCeoReview(requestDto.getContent());
        UpdateCeoReviewResponseDto response = new UpdateCeoReviewResponseDto(
                ceoReview.getContent()
        );
        return ResponseDto.success(response);
    }

    public ResponseDto<String> deleteCeoReview(Long reviewId, String token) {
        CeoReview ceoReview = ceoReviewRepository.findById(reviewId).orElseThrow(()-> new EntityNotFoundException("수정하려는 답글이 없습니다."));
        Long memberId = jwtUtil.extractMemberId(token);

        if(!ceoReview.getMember().getId().equals(memberId)){
            throw new InvalidRequestStateException("가게 사장님에게만 삭제 권한이 있습니다.");
        }
        ceoReviewRepository.delete(ceoReview);
        return ResponseDto.success("답글을 삭제하였습니다.");
    }
}
