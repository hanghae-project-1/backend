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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Tag(name = "Order", description = "주문 생성, 주문내역 조회,수정 등의 사용자 API")
public interface OrderControllerDocs {

	@Operation(summary = "주문 생성", description = "사용자의 ID 를 통해 주문을 생성하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "주문 생성 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "총 금액이 맞지않습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/order")
	Response<Void> createOrder(@Valid @RequestBody OrderRequestDTO request);

	@Operation(summary = "주문 수정", description = "주문 ID 를 통해 주문을 수정하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "주문 수정 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "400", description = "주문을 취소할 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PatchMapping("/api/v1/order/{orderId}")
	Response<Void> modifyOrderStatus(@PathVariable UUID orderId);

}
