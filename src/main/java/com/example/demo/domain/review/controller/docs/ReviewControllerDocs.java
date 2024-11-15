package com.example.demo.domain.review.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import com.example.demo.domain.review.model.response.ReviewListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "Review", description = "리뷰 생성, 리뷰 조회,수정 등의 사용자 API")
public interface ReviewControllerDocs {

	@Operation(summary = "리뷰 생성", description = "주문 ID 를 통해 리뷰를 생성하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "리뷰 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = """
					1. 사용자의 주문이 아닙니다. \n
					2. 리뷰는 구매 확정 후 작성할 수 있습니다.
					""", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/review")
	Response<Void> createReview(@Valid @RequestBody ReviewRequestDTO request);

	@Operation(summary = "리뷰 수정", description = "리뷰 ID 를 통해 리뷰의 상태를 수정하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "리뷰 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = """
					1. 사용자의 주문이 아닙니다. \n
					2. 본인의 주문에만 리뷰를 작성할 수 있습니다.
					""", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PatchMapping("/api/v1/review/{reviewId}")
	Response<Void> modifyReviewStatus(@PathVariable UUID reviewId);

	@Operation(summary = "사용자 리뷰 조회", description = "사용자 ID 를 통해 작성한 리뷰를 조회하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "리뷰 조회 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@GetMapping("/api/v1/review/user/{userId}")
	Response<ReviewListResponseDTO> getUserReviewList(@PathVariable String userId, Pageable pageable);

	@Operation(summary = "가게 리뷰 조회", description = "가게 ID 를 통해 작성된 리뷰를 조회하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "리뷰 조회 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@GetMapping("/api/v1/review/store/{storeId}")
	Response<ReviewListResponseDTO> getStoreReviewList(@PathVariable UUID storeId, Pageable pageable);

}
