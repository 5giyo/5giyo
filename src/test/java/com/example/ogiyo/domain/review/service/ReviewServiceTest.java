package com.example.ogiyo.domain.review.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.s3.S3Manager;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.ceoReview.entity.CeoReview;
import com.example.ogiyo.domain.member.entity.Member;
import com.example.ogiyo.domain.member.service.MemberService;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.service.OrderServiceImpl;
import com.example.ogiyo.domain.photo.domainType.DomainType;
import com.example.ogiyo.domain.photo.repository.PhotoRepository;
import com.example.ogiyo.domain.photo.service.PhotoService;
import com.example.ogiyo.domain.review.dto.request.SaveReviewRequestDto;
import com.example.ogiyo.domain.review.dto.request.UpdateReviewRequestDto;
import com.example.ogiyo.domain.review.dto.response.PagingReviewResponseDto;
import com.example.ogiyo.domain.review.dto.response.SaveReviewResponseDto;
import com.example.ogiyo.domain.review.dto.response.UpdateReviewResponseDto;
import com.example.ogiyo.domain.review.entity.Review;
import com.example.ogiyo.domain.review.repository.ReviewRepository;
import com.example.ogiyo.domain.store.entity.Store;
import com.example.ogiyo.domain.store.service.StoreService;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private StoreService storeService;
    @Mock
    private MemberService memberService;
    @Mock
    private OrderServiceImpl orderService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private S3Manager s3Manager;
    @Mock
    private PhotoService photoService;

    private Member member;
    private Order order;
    private Store store;
    private Review review;

    @BeforeEach
    void setUp() {
        member = new Member();
        store = new Store();
        order = new Order();
        order.updateOrder(OrderStatus.DELIVERED);
    }

    @Nested
    class 리뷰작성테스트{
        @Test
        void 리뷰작성_주문완료상태가_아님(){
            //given
            Long storeId = 1L;
            String token = "validToken";

            order.updateOrder(OrderStatus.PREPARING);
            SaveReviewRequestDto reviewRequestDto = new SaveReviewRequestDto(1L, (byte) 5, "완전 맛집!");

            when(jwtUtil.extractMemberId(token)).thenReturn(1L);
            when(orderService.findOrder(reviewRequestDto.getOrderId())).thenReturn(order);

            //when
            //then
            assertThrows(InvalidRequestStateException.class, () ->
                    reviewService.saveReview(storeId, token, reviewRequestDto, null)
            );
        }
        @Test
        void 리뷰작성_사진이없는경우(){
            //given
            Long storeId = 1L;
            String token = "validToken";
            Long memberId = 1L;

            SaveReviewRequestDto reviewRequestDto = new SaveReviewRequestDto(1L, (byte) 5, "완전 맛집!");
            Review review = new Review(store, member, order, (byte) 5, "Great food!");

            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(orderService.findOrder(reviewRequestDto.getOrderId())).thenReturn(order);
            when(memberService.findById(memberId)).thenReturn(Optional.of(member));
            when(storeService.findStore(storeId)).thenReturn(store);
            when(reviewRepository.save(any(Review.class))).thenReturn(review);

            //when
            ResponseDto<SaveReviewResponseDto> response = reviewService.saveReview(storeId, token, reviewRequestDto, null);

            //then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals(0, response.getData().getPhotoUrls().size());
        }
        @Test
        void 리뷰작성_사진이있는경우() {
            //given
            Long storeId = 1L;
            String token = "validToken";
            Long memberId = 1L;

            SaveReviewRequestDto reviewRequestDto = new SaveReviewRequestDto(1L, (byte) 5, "완전 맛집!");
            Review review = new Review(store, member, order, (byte) 5, "Great food!");
            MultipartFile mockPhoto = mock(MultipartFile.class);
            List<MultipartFile> photos = Collections.singletonList(mockPhoto);

            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(orderService.findOrder(reviewRequestDto.getOrderId())).thenReturn(order);
            when(memberService.findById(memberId)).thenReturn(Optional.of(member));
            when(storeService.findStore(storeId)).thenReturn(store);
            when(reviewRepository.save(any(Review.class))).thenReturn(review);
            when(photoService.findMaxSeq(any())).thenReturn(0);
            when(s3Manager.uploadFile(any(), any())).thenReturn("https://asd.com/test.jpg");

            //when
            ResponseDto<SaveReviewResponseDto> response = reviewService.saveReview(storeId, token, reviewRequestDto, photos);

            //then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals(1, response.getData().getPhotoUrls().size());

            verify(s3Manager, times(1)).uploadFile(any(), any());
            verify(photoService, times(1)).saveAll(any());
        }
        @Test
        void 리뷰작성_사진이2장있는경우() {
            //given
            Long storeId = 1L;
            String token = "validToken";
            Long memberId = 1L;

            SaveReviewRequestDto reviewRequestDto = new SaveReviewRequestDto(1L, (byte) 5, "완전 맛집!");
            Review review = new Review(store, member, order, (byte) 5, "Great food!");
            MultipartFile mockPhoto = mock(MultipartFile.class);
            MultipartFile mockPhoto2 = mock(MultipartFile.class);
            List<MultipartFile> photos = Arrays.asList(mockPhoto, mockPhoto2);

            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(orderService.findOrder(reviewRequestDto.getOrderId())).thenReturn(order);
            when(memberService.findById(memberId)).thenReturn(Optional.of(member));
            when(storeService.findStore(storeId)).thenReturn(store);
            when(reviewRepository.save(any(Review.class))).thenReturn(review);
            when(photoService.findMaxSeq(any())).thenReturn(0);
            when(s3Manager.uploadFile(any(), any())).thenReturn("https://asd.com/test.jpg");

            //when
            ResponseDto<SaveReviewResponseDto> response = reviewService.saveReview(storeId, token, reviewRequestDto, photos);

            //then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals(2, response.getData().getPhotoUrls().size());

            verify(s3Manager, times(2)).uploadFile(any(), any());
            verify(photoService, times(1)).saveAll(any());
        }
    }

    @Nested
    class 리뷰가져오기테스트{
        @Test
        void 리뷰가_없을때(){
            // Given
            Long storeId = 1L;
            int pageNumber = 1;
            int pageSize = 10;
            Byte minRating = 1;
            Byte maxRating = 5;
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<Review> reviewPage = new PageImpl<>(Collections.emptyList(), pageRequest, 0);

            when(reviewRepository.findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(any(), any(), any(), any())).thenReturn(reviewPage);

            // When
            ResponseDto<PagingReviewResponseDto> response = reviewService.getReview(storeId, pageNumber, pageSize, minRating, maxRating);

            // Then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals(0, response.getData().getReviews().size());
        }

        @Test
        void 리뷰에_사장님리뷰가_없을_때(){
            // Given
            Long storeId = 1L;
            int pageNumber = 1;
            int pageSize = 10;
            Byte minRating = 1;
            Byte maxRating = 5;
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Review review = mock(Review.class);
            when(review.getCeoReview()).thenReturn(null);
            when(review.getMember()).thenReturn(mock(Member.class));

            List<Review> reviewList = List.of(review);
            Page<Review> reviewPage = new PageImpl<>(reviewList, pageRequest, reviewList.size());

            when(reviewRepository.findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(any(), any(), any(), any())).thenReturn(reviewPage);
            when(photoService.findByDomainTypeAndDomainKey(any(), any())).thenReturn(Collections.emptyList());

            //when
            ResponseDto<PagingReviewResponseDto> response = reviewService.getReview(storeId, pageNumber, pageSize, minRating, maxRating);

            // Then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals("", response.getData().getReviews().get(0).getCeoComment());
        }

        @Test
        void 리뷰에_사장님리뷰가_있을_때(){
            // Given
            Long storeId = 1L;
            int pageNumber = 1;
            int pageSize = 10;
            Byte minRating = 1;
            Byte maxRating = 5;
            PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Review review = mock(Review.class);
            when(review.getMember()).thenReturn(mock(Member.class));

            List<Review> reviewList = List.of(review);
            Page<Review> reviewPage = new PageImpl<>(reviewList, pageRequest, reviewList.size());

            when(reviewRepository.findAllByStoreIdAndRatingBetweenOrderByModifiedAtDesc(any(), any(), any(), any())).thenReturn(reviewPage);
            when(photoService.findByDomainTypeAndDomainKey(any(), any())).thenReturn(Collections.emptyList());

            CeoReview ceoReview = mock(CeoReview.class);
            when(ceoReview.getContent()).thenReturn("감사합니다. 또 오세요!");
            when(review.getCeoReview()).thenReturn(ceoReview);

            //when
            ResponseDto<PagingReviewResponseDto> response = reviewService.getReview(storeId, pageNumber, pageSize, minRating, maxRating);

            // Then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals("감사합니다. 또 오세요!", response.getData().getReviews().get(0).getCeoComment());
        }
    }

    @Nested
    class 리뷰업데이트테스트{
        @Test
        void 수정하려는_리뷰를_찾을_수_없을_때(){
            //given
            Long reviewId = 1L;
            UpdateReviewRequestDto requestDto = new UpdateReviewRequestDto((byte) 5, "수정된 리뷰입니다.");
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            //when
            //then
            assertThrows(IllegalArgumentException.class, () ->
                    reviewService.updateReview(reviewId, "validToken", requestDto)
            );
        }

        @Test
        void 리뷰작성자와_수정요청자가_다를_때(){
            //given
            Long reviewId = 1L;
            Long memberId = 1L;
            Member otherMember = new Member();
            review = new Review(null, otherMember, null, (byte) 5, "기존 리뷰입니다.");
            UpdateReviewRequestDto requestDto = new UpdateReviewRequestDto((byte) 5, "수정된 리뷰입니다.");

            when(jwtUtil.extractMemberId(any())).thenReturn(memberId);
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
            //when
            //then
            assertThrows(IllegalArgumentException.class, () ->
                    reviewService.updateReview(reviewId, "validToken", requestDto));
        }

        @Test
        void 리뷰_정상_수정() {
            // given
            Long reviewId = 1L;
            Long memberId = 1L;
            String token = "validToken";
            UpdateReviewRequestDto requestDto = new UpdateReviewRequestDto((byte) 5, "수정된 리뷰입니다.");

            Member mockMember = mock(Member.class);
            when(mockMember.getId()).thenReturn(memberId);

            Review mockReview = mock(Review.class);
            when(mockReview.getMember()).thenReturn(mockMember);
            when(mockReview.getRating()).thenReturn((byte) 5);
            when(mockReview.getContent()).thenReturn("수정된 리뷰입니다.");


            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));

            // when
            ResponseDto<UpdateReviewResponseDto> response = reviewService.updateReview(reviewId, token, requestDto);

            // then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals(requestDto.getRating(), response.getData().getRating());
            assertEquals(requestDto.getContent(), response.getData().getContent());
        }
    }

    @Nested
    class 리뷰삭제{
        @Test
        void 삭제하려는_리뷰를_찾을_수_없을_때(){
            //given
            Long reviewId = 1L;

            when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

            //when & then
            assertThrows(IllegalArgumentException.class, () ->
                    reviewService.deleteReview(reviewId, "validToken"));
        }
        @Test
        void 작성된_리뷰와_삭제_요청자가_다를_떼(){
            //given
            Long reviewId = 1L;
            Long memberId = 1L;
            Long otherMemberId = 2L;
            String token = "validToken";

            Member otherMember = mock(Member.class);
            when(otherMember.getId()).thenReturn(otherMemberId);

            Review mockReview = mock(Review.class);
            when(mockReview.getMember()).thenReturn(otherMember);

            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));

            //when & then
            assertThrows(IllegalArgumentException.class, () ->
                    reviewService.deleteReview(reviewId, token));
            //실제로 삭제 메서드가 호출되지 않았는지 확인.
            verify(reviewRepository, never()).deleteById(any());
            verify(photoService, never()).deleteByDomainTypeAndDomainKey(any(), any());
        }

        @Test
        void 리뷰사진이_없을_때_정상삭제(){
            //given
            Long reviewId = 1L;
            Long memberId = 1L;
            String token = "validToken";

            Member mockMember = mock(Member.class);
            when(mockMember.getId()).thenReturn(memberId);

            Review mockReview = mock(Review.class);
            when(mockReview.getMember()).thenReturn(mockMember);
            when(mockReview.getId()).thenReturn(reviewId);

            when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
            when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));

            //when
            ResponseDto<String> response = reviewService.deleteReview(reviewId, token);

            //then
            assertNotNull(response);
            assertTrue(response.getSuccess());
            assertEquals("리뷰를 삭제하였습니다.", response.getData());

            //photoService의 삭제메서드 1회, S3 삭제 메서드 0회, reviewRepository삭제메서드 1회
            verify(photoService, times(1)).deleteByDomainTypeAndDomainKey(DomainType.REVIEW, reviewId);
            verify(s3Manager, times(0)).deleteFile(any());
            verify(reviewRepository, times(1)).deleteById(reviewId);
        }
    }
}
