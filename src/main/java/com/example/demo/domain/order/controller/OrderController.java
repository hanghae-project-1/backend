package com.example.demo.domain.order.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.order.controller.docs.OrderControllerDocs;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.model.response.OrderResponseDTO;
import com.example.demo.domain.order.model.response.StoreOrderResponseDTO;
import com.example.demo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController implements OrderControllerDocs {

	private final OrderService orderService;

	@PostMapping
	public Response<Void> createOrder(@Valid @RequestBody OrderRequestDTO request) {

		orderService.createOrder(request);

		return Response.<Void>builder()
				.code(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.build();
	}

	@PatchMapping("/{orderId}")
	public Response<Void> modifyOrderStatus(@PathVariable UUID orderId, @RequestParam Boolean isCancel) {

		orderService.modifyOrderStatus(orderId, isCancel);

		return Response.<Void>builder()
				.build();
	}

	@GetMapping("/{orderId}")
	public Response<OrderResponseDTO> getOrderDetails(@PathVariable UUID orderId) {

		return Response.<OrderResponseDTO>builder()
				.data(orderService.getOrderDetails(orderId))
				.build();
	}

	@GetMapping("/user")
	public Response<List<OrderResponseDTO>> getAllOrdersByCustomer() {

		return Response.<List<OrderResponseDTO>>builder()
				.data(orderService.getAllOrdersByCustomer())
				.build();
	}

	@GetMapping("/store/{storeId}")
	public Response<StoreOrderResponseDTO> getAllOrdersByStore(@PathVariable UUID storeId,
	                                                           @RequestBody LocalDateTime startDate,
	                                                           @RequestBody LocalDateTime endDate) {

		return Response.<StoreOrderResponseDTO>builder()
				.data(orderService.getAllOrdersByStore(storeId, startDate, endDate))
				.build();
	}
}
