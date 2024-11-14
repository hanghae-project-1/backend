package com.example.demo.domain.review.mapper;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.review.entity.Review;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

	public Review toEntity(ReviewRequestDTO request) {

		return Review.builder()
				.order(Order.builder().id(request.orderId()).build())
				.content(request.base().content())
				.rating(request.base().rating())
				.build();
	}

	public ReviewResponseDTO toReviewResponseDTO(Review review) {
		return new ReviewResponseDTO(
				review.getContent(),
				review.getRating(),
				review.getImageUrl()
		);
	}
}
