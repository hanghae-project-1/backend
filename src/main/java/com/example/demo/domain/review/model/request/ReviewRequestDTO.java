package com.example.demo.domain.review.model.request;

import java.util.UUID;

public record ReviewRequestDTO(

		UUID orderId,

		BaseReviewRequestDTO base
) {
}
