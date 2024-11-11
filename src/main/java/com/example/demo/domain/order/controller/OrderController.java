package com.example.demo.domain.order.controller;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.order.controller.docs.OrderControllerDocs;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
