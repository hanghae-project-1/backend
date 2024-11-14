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
import com.example.demo.domain.review.model.response.UserReviewResponseDTO;
import com.example.demo.domain.review.repository.ReviewRepository;
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

	private final ReviewMapper reviewMapper;
	private final OrderRepository orderRepository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public void createReview(@Valid ReviewRequestDTO request, UUID userId) {

		orderRepository.findById(request.orderId()).ifPresentOrElse(order -> {

					validateOrderByUser(order.getCreatedBy(), userId);
					validateOrderStatus(order.getStatus());

					reviewRepository.save(reviewMapper.toEntity(request));
				}, () -> {
					throw new NotFoundOrderException();
				}
		);

	}

	@Transactional
	public void modifyReviewStatus(UUID reviewId, UUID userId) {

		reviewRepository.findById(reviewId).ifPresentOrElse(review -> {

			validateOrderByUser(review.getOrder().getCreatedBy(), userId);
			validateReviewByUser(review.getCreatedBy(), userId);

			review.markAsDelete();

			reviewRepository.save(review);
		}, () -> {
			throw new NotFoundReviewException();
		});

	}

	@Transactional(readOnly = true)
	public UserReviewResponseDTO getReviewList(UUID userId, Pageable pageable) {

		Page<Review> userReviewList = reviewRepository.findAllByCreatedBy(userId, pageable);

		return new UserReviewResponseDTO(
				userReviewList.getNumberOfElements(),
				userReviewList.getContent().stream().map(reviewMapper::toReviewResponseDTO).toList()
		);
	}

	private void validateReviewByUser(UUID reviewId, UUID userId) {
		if (!reviewId.equals(userId)) {
			throw new IsNotYourReviewException();
		}
	}

	private void validateOrderStatus(Status.Order status) {
		if (!status.equals(ORDER_COMPLETED)) {
			throw new PurchaseIsNotConfirmedException();
		}
	}

	private void validateOrderByUser(UUID orderId, UUID userId) {
		if (!orderId.equals(userId)) {
			throw new IsNotYourOrderException();
		}
	}

}
