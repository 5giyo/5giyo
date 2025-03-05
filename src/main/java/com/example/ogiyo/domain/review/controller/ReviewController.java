package com.example.ogiyo.domain.review.controller;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.domain.review.dto.request.SaveReviewRequestDto;
import com.example.ogiyo.domain.review.dto.request.UpdateReviewRequestDto;
import com.example.ogiyo.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/{storeId}/review")
    public ResponseEntity<ResponseDto<?>> saveReview(@PathVariable Long storeId,
                                                     @RequestHeader(JwtProperties.HEADER_STRING) String token,
                                                     @RequestPart ("saveReview") @Valid SaveReviewRequestDto saveReviewRequestDto,
                                                     @RequestPart (value = "photos", required = false) List<MultipartFile> photos) {
        return ResponseEntity.ok(reviewService.saveReview(storeId, token, saveReviewRequestDto, photos));
    }

    @GetMapping("/{storeId}/review")
    public ResponseEntity<ResponseDto<?>> getReview(@PathVariable Long storeId,
                                                    @RequestParam(defaultValue = "1") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(reviewService.getReview(storeId, pageNumber, pageSize));
    }


    @PutMapping("/{storeId}/review/{reviewId}")
    public ResponseEntity<ResponseDto<?>> updateReview(
            @PathVariable Long reviewId,
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @RequestBody UpdateReviewRequestDto updateReviewRequestDto){
        return ResponseEntity.ok(reviewService.updateReview(reviewId, token, updateReviewRequestDto));
    }

    @DeleteMapping("/{storeId}/review/{reviewId}")
    public ResponseEntity<ResponseDto<?>> deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader(JwtProperties.HEADER_STRING) String token){
        return ResponseEntity.ok(reviewService.deleteReview(reviewId, token));
    }
}
