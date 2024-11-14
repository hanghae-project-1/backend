package com.example.demo.domain.review.service;

import com.example.demo.domain.order.exception.NotFoundOrderException;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.review.exception.IsNotYourReviewException;
import com.example.demo.domain.review.exception.PurchaseIsNotConfirmedException;
import com.example.demo.domain.review.mapper.ReviewMapper;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

					if (!order.getCreatedBy().equals(userId)) {
						throw new IsNotYourReviewException();
					}

					if (!order.getStatus().equals(ORDER_COMPLETED)) {
						throw new PurchaseIsNotConfirmedException();
					}

					reviewRepository.save(reviewMapper.toEntity(request));
				}, () -> {
					throw new NotFoundOrderException();
				}
		);

	}
}
