package com.example.demo.domain.review.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.entity.user.User;
import com.example.demo.domain.review.model.request.ReviewRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Review", description = "리뷰 생성, 리뷰 조회,수정 등의 사용자 API")
public interface ReviewControllerDocs {

	@Operation(summary = "리뷰 생성", description = "주문 ID 를 통해 리뷰를 생성하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "리뷰 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = """
					1. 리뷰는 구매 확정 후 작성할 수 있습니다. \n
					2. 본인의 주문에만 리뷰를 작성할 수 있습니다.
					""", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/review")
	Response<Void> createReview(@Valid @RequestBody ReviewRequestDTO request, @AuthenticationPrincipal User user);

}
