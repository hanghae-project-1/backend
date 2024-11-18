package com.example.demo.domain.review.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.review.controller.docs.ReviewControllerDocs;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewListResponseDTO;
import com.example.demo.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController implements ReviewControllerDocs {

	private final ReviewService reviewService;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_CUSTOMER')")
	public Response<Void> createReview(@Valid @RequestBody ReviewRequestDTO request) {

		reviewService.createReview(request);
		return Response.<Void>builder()
				.build();
	}

	@PatchMapping("/{reviewId}")
	@PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER', 'ROLE_MASTER')")
	public Response<Void> modifyReviewStatus(@PathVariable UUID reviewId) {
		reviewService.modifyReviewStatus(reviewId);
		return Response.<Void>builder()
				.build();
	}

	@GetMapping("/user/{userId}")
	public Response<ReviewListResponseDTO> getUserReviewList(@PathVariable String userId,
	                                                         Pageable pageable) {
		return Response.<ReviewListResponseDTO>builder()
				.data(reviewService.getUserReviewList(userId, pageable))
				.build();
	}

	@GetMapping("/store/{storeId}")
	public Response<ReviewListResponseDTO> getStoreReviewList(@PathVariable UUID storeId,
	                                                          Pageable pageable) {
		return Response.<ReviewListResponseDTO>builder()
				.data(reviewService.getStoreReviewList(storeId, pageable))
				.build();
	}
}
