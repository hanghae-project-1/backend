package com.example.demo.domain.order.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Order", description = "상품 구매, 주문내역 조회,수정 등의 사용자 API")
public interface OrderControllerDocs {

	@Operation(summary = "상품 구매", description = "사용자의 ID 를 통해 주문을 생성하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "주문 생성 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/order")
	Response<Void> createOrder(@Valid @RequestBody OrderRequestDTO request);

}
