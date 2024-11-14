package com.example.demo.domain.review.model.response;

public record ReviewResponseDTO(

		String content,

		Integer rating,

		String imageUrl
) {
}
