package com.example.demo.domain.review.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.entity.user.User;
import com.example.demo.domain.review.controller.docs.ReviewControllerDocs;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewListResponseDTO;
import com.example.demo.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController implements ReviewControllerDocs {

	private final ReviewService reviewService;

	@PostMapping
	public Response<Void> createReview(@Valid @RequestBody ReviewRequestDTO request, @AuthenticationPrincipal User user) {

		reviewService.createReview(request, user.getId());
		return Response.<Void>builder()
				.build();
	}

	@PatchMapping("/{reviewId}")
	public Response<Void> modifyReviewStatus(@PathVariable UUID reviewId, @AuthenticationPrincipal User user) {
		reviewService.modifyReviewStatus(reviewId, user.getId());
		return Response.<Void>builder()
				.build();
	}

	@GetMapping("/user/{userId}")
	public Response<ReviewListResponseDTO> getUserReviewList(@PathVariable UUID userId,
	                                                         @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
	                                                         Pageable pageable) {
		return Response.<ReviewListResponseDTO>builder()
				.data(reviewService.getUserReviewList(userId, pageable))
				.build();
	}

	@GetMapping("/store/{storeId}")
	public Response<ReviewListResponseDTO> getStoreReviewList(@PathVariable UUID storeId,
	                                                          @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
	                                                          Pageable pageable) {
		return Response.<ReviewListResponseDTO>builder()
				.data(reviewService.getStoreReviewList(storeId, pageable))
				.build();
	}
}
