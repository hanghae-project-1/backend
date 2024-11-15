package com.example.demo.domain.review.service;

import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.exception.IsNotYourOrderException;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.exception.IsNotYourReviewException;
import com.example.demo.domain.review.exception.NotFoundReviewException;
import com.example.demo.domain.review.exception.PurchaseIsNotConfirmedException;
import com.example.demo.domain.review.mapper.ReviewMapper;
import com.example.demo.domain.review.model.request.BaseReviewRequestDTO;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.domain.user.common.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;
import static com.example.demo.domain.entity.common.Status.Order.SHIPPING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;

	@Mock
	private ReviewRepository reviewRepository;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private UserService userService;

	@Mock
	private ReviewMapper reviewMapper;

	@Nested
	@DisplayName("리뷰 생성 테스트")
	class createReviewTest {

		private Order createMockOrder(UUID orderId, String userId, LocalDateTime createdAt) {

			Order order = new Order();
			ReflectionTestUtils.setField(order, "id", orderId);
			ReflectionTestUtils.setField(order, "createdAt", createdAt);
			ReflectionTestUtils.setField(order, "createdBy", userId);
			ReflectionTestUtils.setField(order, "status", ORDER_COMPLETED);

			return order;
		}

		private Review createMockReview(UUID id, Order mockOrder) {

			Review review = new Review();
			ReflectionTestUtils.setField(review, "id", id);
			ReflectionTestUtils.setField(review, "content", "content");
			ReflectionTestUtils.setField(review, "rating", 4);
			ReflectionTestUtils.setField(review, "order", mockOrder);
			return review;
		}

		@Test
		@DisplayName("리뷰 생성 성공")
		void createReview_SUCCESS() {

			// Given
			UUID orderId = UUID.randomUUID();
			UUID reviewId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			Order mockOrder = createMockOrder(orderId, userId, LocalDateTime.now().minusMinutes(3));
			Review mockReview = createMockReview(reviewId, mockOrder);

			ReviewRequestDTO request = new ReviewRequestDTO(
					orderId,
					new BaseReviewRequestDTO(
							"content",
							4
					)
			);

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
			when(reviewMapper.toEntity(request)).thenReturn(mockReview);
			when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);
			when(userService.getCurrentUsername()).thenReturn(userId);

			// When
			reviewService.createReview(request);

			// Then
			ArgumentCaptor<Review> reviewArgumentCaptor = ArgumentCaptor.forClass(Review.class);
			verify(reviewRepository, times(1)).save(reviewArgumentCaptor.capture());
			Review review = reviewArgumentCaptor.getValue();

			assertThat(review.getOrder().getId()).isEqualTo(orderId);
			assertThat(review.getContent()).isEqualTo("content");
			assertThat(review.getRating()).isEqualTo(4);
			assertThat(review.getOrder().getCreatedBy()).isEqualTo(userId);
		}

		@Test
		@DisplayName("리뷰 생성 실패 _ 내 주문이 아님")
		void createReview_FAIL_IS_NOT_YOUR_ORDER() {

			// Given
			UUID orderId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			Order mockOrder = createMockOrder(orderId, userId, LocalDateTime.now().minusMinutes(3));

			ReviewRequestDTO request = new ReviewRequestDTO(
					orderId,
					new BaseReviewRequestDTO(
							"content",
							4
					)
			);

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

			// When & Then
			assertThrows(
					IsNotYourOrderException.class,
					() -> reviewService.createReview(request)
			);

		}

		@Test
		@DisplayName("리뷰 생성 실패 _ 구매 확정이 아님")
		void createReview_FAIL_PURCHASE_IS_NOT_CONFIRMED() {

			// Given
			UUID orderId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			Order mockOrder = createMockOrder(orderId, userId, LocalDateTime.now().minusMinutes(3));

			ReflectionTestUtils.setField(mockOrder, "status", SHIPPING);

			ReviewRequestDTO request = new ReviewRequestDTO(
					orderId,
					new BaseReviewRequestDTO(
							"content",
							4
					)
			);

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
			when(userService.getCurrentUsername()).thenReturn(userId);

			// When & Then
			assertThrows(
					PurchaseIsNotConfirmedException.class,
					() -> reviewService.createReview(request)
			);

		}

	}

	@Nested
	@DisplayName("리뷰 수정 테스트")
	class modifyReviewStatusTest {

		private Order createMockOrder(UUID orderId, String userId, LocalDateTime createdAt) {

			Order order = new Order();
			ReflectionTestUtils.setField(order, "id", orderId);
			ReflectionTestUtils.setField(order, "createdAt", createdAt);
			ReflectionTestUtils.setField(order, "createdBy", userId);
			ReflectionTestUtils.setField(order, "status", ORDER_COMPLETED);

			return order;
		}

		private Review createMockReview(UUID reviewId, String userId, UUID orderId) {

			Review review = new Review();
			ReflectionTestUtils.setField(review, "id", reviewId);
			ReflectionTestUtils.setField(review, "content", "content");
			ReflectionTestUtils.setField(review, "rating", 4);
			ReflectionTestUtils.setField(review, "createdBy", userId);
			ReflectionTestUtils.setField(review, "order", createMockOrder(orderId, userId, LocalDateTime.now().minusMinutes(3)));
			return review;
		}

		@Test
		@DisplayName("리뷰 상태값 변경 성공")
		void modifyReviewStatus_SUCCESS() {

			// Given
			UUID reviewId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			UUID orderId = UUID.randomUUID();
			Review mockReview = createMockReview(reviewId, userId, orderId);

			when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
			when(reviewRepository.save(any(Review.class))).thenReturn(mockReview);
			when(userService.getCurrentUsername()).thenReturn(userId);

			// When
			reviewService.modifyReviewStatus(reviewId);

			// Then
			assertTrue(mockReview.getIsDelete());
			assertFalse(mockReview.getIsPublic());
			assertNotNull(mockReview.getDeletedAt());
			verify(reviewRepository).save(mockReview);
		}

		@Test
		@DisplayName("리뷰 상태값 변경 실패 _ 내 주문이 아님")
		void modifyReviewStatus_FAIL_IS_NOT_YOUR_ORDER() {

			// Given
			UUID reviewId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			UUID orderId = UUID.randomUUID();
			Review mockReview = createMockReview(reviewId, userId, orderId);

			when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));

			// When & Then
			assertThrows(
					IsNotYourOrderException.class,
					() -> reviewService.modifyReviewStatus(reviewId)
			);
		}

		@Test
		@DisplayName("리뷰 상태값 변경 실패 _ 내 리뷰가 아님")
		void modifyReviewStatus_FAIL_IS_NOT_YOUR_REVIEW() {

			// Given
			UUID reviewId = UUID.randomUUID();
			String userId = String.valueOf(UUID.randomUUID());
			UUID orderId = UUID.randomUUID();
			Review mockReview = createMockReview(reviewId, userId, orderId);

			ReflectionTestUtils.setField(mockReview, "createdBy", String.valueOf(UUID.randomUUID()));

			when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(mockReview));
			when(userService.getCurrentUsername()).thenReturn(userId);

			// When & Then
			assertThrows(
					IsNotYourReviewException.class,
					() -> reviewService.modifyReviewStatus(reviewId)
			);
		}

		@Test
		@DisplayName("리뷰 상태값 변경 실패 _ 리뷰를 찾을 수 없음")
		void modifyReviewStatus_FAIL_NOT_FOUND_REVIEW() {

			// Given
			UUID reviewId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();

			when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());

			// When & Then
			assertThrows(
					NotFoundReviewException.class,
					() -> reviewService.modifyReviewStatus(reviewId)
			);
		}
	}

	@Nested
	@DisplayName("리뷰 목록 조회 테스트")
	class getReview {

		private List<Review> createMockReviewList(String userId) {

			Store store = new Store();
			ReflectionTestUtils.setField(store, "id", UUID.randomUUID());

			Order order = new Order();
			ReflectionTestUtils.setField(order, "id", UUID.randomUUID());
			ReflectionTestUtils.setField(order, "store", store);

			Review review1 = new Review();
			ReflectionTestUtils.setField(review1, "id", UUID.randomUUID());
			ReflectionTestUtils.setField(review1, "content", "Great product!");
			ReflectionTestUtils.setField(review1, "rating", 5);
			ReflectionTestUtils.setField(review1, "createdBy", userId);
			ReflectionTestUtils.setField(review1, "order", order);

			Review review2 = new Review();
			ReflectionTestUtils.setField(review2, "id", UUID.randomUUID());
			ReflectionTestUtils.setField(review2, "content", "Not bad.");
			ReflectionTestUtils.setField(review2, "rating", 3);
			ReflectionTestUtils.setField(review2, "createdBy", userId);
			ReflectionTestUtils.setField(review2, "order", order);

			return List.of(review1, review2);
		}

		@Test
		@DisplayName("사용자 ID로 작성한 리뷰 목록 조회 성공")
		void getUserReviewList_SUCCESS() {

			// Given
			String userId = String.valueOf(UUID.randomUUID());

			Pageable pageable = PageRequest.of(0, 10);
			Page<Review> emptyPage = new PageImpl<>(createMockReviewList(userId), pageable, 2);

			when(reviewRepository.findAllByCreatedBy(userId, pageable)).thenReturn(emptyPage);

			// When
			reviewService.getUserReviewList(userId, pageable);

			// Then
			verify(reviewRepository, times(1)).findAllByCreatedBy(userId, pageable);
		}

		@Test
		@DisplayName("가게 ID로 작성된 리뷰 목록 조회 성공")
		void getStoreReviewList_SUCCESS() {

			// Given
			UUID storeId = UUID.randomUUID();

			Pageable pageable = PageRequest.of(0, 10);

			Page<Review> emptyPage = new PageImpl<>(createMockReviewList(String.valueOf(UUID.randomUUID())), pageable, 2);

			when(reviewRepository.findAllByIsDeleteTrueAndIsPublicFalseAndOrderStoreId(storeId, pageable)).thenReturn(emptyPage);

			// When
			reviewService.getStoreReviewList(storeId, pageable);

			// Then
			verify(reviewRepository, times(1)).findAllByIsDeleteTrueAndIsPublicFalseAndOrderStoreId(storeId, pageable);
		}
	}
}