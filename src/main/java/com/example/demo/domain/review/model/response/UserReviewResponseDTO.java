package com.example.demo.domain.review.model.response;

import java.util.List;

public record UserReviewResponseDTO(

		Integer totalElements,

		List<ReviewResponseDTO> reviewList

) {
}
