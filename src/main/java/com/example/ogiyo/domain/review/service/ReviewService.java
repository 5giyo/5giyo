package com.example.ogiyo.domain.review.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.s3.S3Manager;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.service.OrderServiceImpl;
import com.example.ogiyo.domain.photo.domainType.DomainType;
import com.example.ogiyo.domain.photo.dto.PhotoUrlResponse;
import com.example.ogiyo.domain.photo.entity.Photo;
import com.example.ogiyo.domain.photo.service.PhotoService;
import com.example.ogiyo.domain.review.dto.response.UpdateReviewResponseDto;
import com.example.ogiyo.domain.store.service.StoreService;
import com.example.ogiyo.domain.review.dto.request.SaveReviewRequestDto;
import com.example.ogiyo.domain.review.dto.request.UpdateReviewRequestDto;
import com.example.ogiyo.domain.review.dto.response.GetReviewResponseDto;
import com.example.ogiyo.domain.review.dto.response.PagingReviewResponseDto;
import com.example.ogiyo.domain.review.dto.response.SaveReviewResponseDto;
import com.example.ogiyo.domain.review.entity.Review;
import com.example.ogiyo.domain.review.repository.ReviewRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final StoreService storeService;
    private final MemberService memberService;
    private final OrderServiceImpl orderService;
    private final PhotoService photoService;
    private final JwtUtil jwtUtil;
    private final S3Manager s3Manager;

    @Transactional
    public ResponseDto<SaveReviewResponseDto> saveReview(Long storeId, String token, SaveReviewRequestDto reviewRequestDto, List<MultipartFile> photos) {
        Long memberId = jwtUtil.extractMemberId(token);

        Order order = orderService.findOrder(reviewRequestDto.getOrderId());
        OrderStatus orderStatus = order.getOrderStatus();
        if(!OrderStatus.DELIVERED.equals(orderStatus)) {
            throw new InvalidRequestStateException("배달 완료된 주문만 리뷰를 작성할 수 있습니다.");
        }
        Member member = memberService.findById(memberId).orElseThrow(()-> new EntityNotFoundException("회원을 찾지 못했습니다."));

        Review review = new Review(
                storeService.getStore(storeId),
                member,
                order,
                reviewRequestDto.getRating(),
                reviewRequestDto.getContent()
        );
        Review savedReview = reviewRepository.save(review);

        List<Photo> photoList = new ArrayList<>();
        List<PhotoUrlResponse> photoUrlResponseList = new ArrayList<>();

        if(photos != null) {
            int imageSeq = photoService.findMaxSeq(savedReview.getId());
            for (MultipartFile photo : photos) {
                String photoKeyName = s3Manager.generateReviewPhotoKeyName();
                String photourl = s3Manager.uploadFile(photoKeyName, photo);

                Photo photoObj = new Photo(
                        DomainType.REVIEW,
                        savedReview.getId(),
                        ++imageSeq,
                        photoKeyName,
                        photourl
                );
                photoList.add(photoObj);
                photoUrlResponseList.add(new PhotoUrlResponse(photourl));
            }
        }
        photoService.saveAll(photoList);

        return ResponseDto.success(buildSaveReviewResponseDto(savedReview, photoUrlResponseList));
    }

    private SaveReviewResponseDto buildSaveReviewResponseDto(Review review, List<PhotoUrlResponse> photoUrlResponseList) {
        SaveReviewResponseDto response = new SaveReviewResponseDto(
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                photoUrlResponseList,
                review.getModifiedAt()
        );
        return response;
    }

    public ResponseDto<PagingReviewResponseDto> getReview(Long storeId, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Review> reviewPage = reviewRepository.findAllByStoreIdOrderByModifiedAtDesc(storeId, pageRequest);

        List<GetReviewResponseDto> reviews = reviewPage.getContent().stream()
                .map(review -> {
                    List<PhotoUrlResponse> photoUrls = photoService.findByDomainTypeAndDomainKey(DomainType.REVIEW, review.getId());
                    String ceoComment = (review.getCeoReview() != null) ? review.getCeoReview().getContent() : "";

                    return new GetReviewResponseDto(
                        review.getId(),
                        review.getMember().getName(),
                        review.getModifiedAt(),
                        review.getRating(),
                        photoUrls,
                        review.getContent(),
                        ceoComment
                    );
                }).toList();

        PagingReviewResponseDto response = new PagingReviewResponseDto(
                reviews,
                (int) reviewPage.getTotalElements(),
                reviewPage.getTotalPages(),
                reviewPage.getNumber() + 1
        );
        return ResponseDto.success(response);
    }

    @Transactional
    public ResponseDto<UpdateReviewResponseDto> updateReview(Long reviewId, String token, UpdateReviewRequestDto requestDto) {
        Long memberId = jwtUtil.extractMemberId(token);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("수정하려는 리뷰를 찾을 수 없습니다."));
        if(!memberId.equals(review.getMember().getId())) {
            throw new IllegalArgumentException("리뷰 작성자만 수정할 수 있습니다.");
        }

        review.updateReview(requestDto.getRating(), requestDto.getContent());
        UpdateReviewResponseDto response = new UpdateReviewResponseDto(
                review.getMember().getName(),
                review.getRating(),
                review.getContent(),
                review.getModifiedAt()
        );
        return ResponseDto.success(response);
    }

    @Transactional
    public ResponseDto<String> deleteReview(Long reviewId, String token) {
        Long memberId = jwtUtil.extractMemberId(token);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new IllegalArgumentException("수정하려는 리뷰를 찾을 수 없습니다."));

        if(!memberId.equals(review.getMember().getId())) {
            throw new IllegalArgumentException("리뷰 작성자만 삭제할 수 있습니다.");
        }

        photoService.deleteByDomainTypeAndDomainKey(DomainType.REVIEW, review.getId());
        reviewRepository.deleteById(reviewId);
        return ResponseDto.success("리뷰를 삭제하였습니다.");
    }


    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(()->new EntityNotFoundException("해당하는 리뷰가 존재하지 않습니다."));
    }
}
