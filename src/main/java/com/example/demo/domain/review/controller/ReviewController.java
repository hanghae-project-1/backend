package com.example.demo.domain.review.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.entity.user.User;
import com.example.demo.domain.review.controller.docs.ReviewControllerDocs;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
