package com.example.demo.domain.order.controller.docs;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.model.response.OrderResponseDTO;
import com.example.demo.domain.order.model.response.StoreOrderResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
	Response<Void> modifyOrderStatus(@PathVariable UUID orderId, @RequestParam Boolean isCancel);

	@Operation(summary = "주문 조회", description = "주문 ID 를 통해 주문을 조회하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "주문 조회 성공", content = @Content(schema = @Schema(implementation = Response.class))),
			@ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다.", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@GetMapping("/api/v1/order/{orderId}")
	Response<OrderResponseDTO> getOrderDetails(@PathVariable UUID orderId);

	@Operation(summary = "내 주문 전체 조회", description = "사용자 ID 를 통해 주문 전체를 조회하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "주문 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@GetMapping("/api/v1/order/user")
	Response<List<OrderResponseDTO>> getAllOrdersByCustomer();

	@Operation(summary = "가게 주문 조회", description = "사용자 ID 를 통해 주문 전체를 조회하는 API 입니다.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "주문 조회 성공", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@GetMapping("/api/v1/order/store/{storeId}")
	Response<StoreOrderResponseDTO> getAllOrdersByStore(@PathVariable UUID storeId,
	                                                    @RequestBody LocalDateTime startDate,
	                                                    @RequestBody LocalDateTime endDate);

}
