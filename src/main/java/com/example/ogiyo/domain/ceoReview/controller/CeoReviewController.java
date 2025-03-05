package com.example.ogiyo.domain.ceoReview.controller;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.domain.ceoReview.dto.request.SaveCeoReviewRequestDto;
import com.example.ogiyo.domain.ceoReview.dto.request.UpdateCeoReviewRequestDto;
import com.example.ogiyo.domain.ceoReview.service.CeoReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review/{reviewId}/ceo-review")
public class CeoReviewController {
    private final CeoReviewService ceoReviewService;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> saveCeoReview(
            @PathVariable Long reviewId,
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @RequestBody SaveCeoReviewRequestDto requestDto
    ) {
        return ResponseEntity.ok(ceoReviewService.saveCeoReview(reviewId, token, requestDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto<?>> updateCeoReview(
            @PathVariable Long reviewId,
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @RequestBody UpdateCeoReviewRequestDto requestDto
            ){
        return ResponseEntity.ok(ceoReviewService.updateCeoReview(reviewId, token, requestDto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<?>> deleteCeoReview(
            @PathVariable Long reviewId,
            @RequestHeader(JwtProperties.HEADER_STRING) String token){
        return ResponseEntity.ok(ceoReviewService.deleteCeoReview(reviewId, token));
    }
}
