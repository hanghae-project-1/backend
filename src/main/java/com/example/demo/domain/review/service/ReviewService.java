package com.example.demo.domain.review.service;

import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.exception.IsNotYourOrderException;
import com.example.demo.domain.order.exception.NotFoundOrderException;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.exception.IsNotYourReviewException;
import com.example.demo.domain.review.exception.NotFoundReviewException;
import com.example.demo.domain.review.exception.PurchaseIsNotConfirmedException;
import com.example.demo.domain.review.mapper.ReviewMapper;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewListResponseDTO;
import com.example.demo.domain.review.repository.ReviewRepository;
import com.example.demo.domain.user.common.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final UserService userService;
	private final ReviewMapper reviewMapper;
	private final OrderRepository orderRepository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public void createReview(@Valid ReviewRequestDTO request) {


		orderRepository.findById(request.orderId()).ifPresentOrElse(order -> {

					validateOrderByUser(order.getCreatedBy(), userService.getCurrentUsername());
					validateOrderStatus(order.getStatus());

					reviewRepository.save(reviewMapper.toEntity(request));
				}, () -> {
					throw new NotFoundOrderException();
				}
		);

	}

	@Transactional
	public void modifyReviewStatus(UUID reviewId) {

		reviewRepository.findById(reviewId).ifPresentOrElse(review -> {

			String currentUsername = userService.getCurrentUsername();
			validateOrderByUser(review.getOrder().getCreatedBy(), currentUsername);
			validateReviewByUser(review.getCreatedBy(), currentUsername);

			review.markAsDelete();

			reviewRepository.save(review);
		}, () -> {
			throw new NotFoundReviewException();
		});

	}

	@Transactional(readOnly = true)
	public ReviewListResponseDTO getUserReviewList(String userId, Pageable pageable) {

		Page<Review> userReviewList = reviewRepository.findAllByCreatedBy(userId, pageable);

		return new ReviewListResponseDTO(
				userReviewList.getNumberOfElements(),
				userReviewList.getContent().stream().map(reviewMapper::toReviewResponseDTO).toList()
		);
	}

	@Transactional(readOnly = true)
	public ReviewListResponseDTO getStoreReviewList(UUID storeId, Pageable pageable) {

		Page<Review> storeReviewList = reviewRepository.findAllByIsDeleteTrueAndIsPublicFalseAndOrderStoreId(storeId, pageable);

		return new ReviewListResponseDTO(
				storeReviewList.getNumberOfElements(),
				storeReviewList.getContent().stream().map(reviewMapper::toReviewResponseDTO).toList()
		);
	}

	private void validateReviewByUser(String createBy, String userId) {
		if (!createBy.equals(userId)) {
			throw new IsNotYourReviewException();
		}
	}

	private void validateOrderStatus(Status.Order status) {
		if (!status.equals(ORDER_COMPLETED)) {
			throw new PurchaseIsNotConfirmedException();
		}
	}

	private void validateOrderByUser(String createBy, String userId) {
		if (!createBy.equals(userId)) {
			throw new IsNotYourOrderException();
		}
	}
}
