package com.example.demo.domain.order.model.request;

import com.example.demo.domain.entity.Order;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;
import static com.example.demo.domain.entity.common.Status.Order.ORDER_PROGRESS;

public record OrderRequestDTO(

		@NotBlank(message = "총 가격을 입력해주세요.")
		Integer totalPrice,

		@NotBlank(message = "도착지를 입력해주세요.")
		String destinationAddr,

		String orderRequest,

		@NotBlank(message = "포장 여부를 입력해주세요.")
		Boolean isTakeOut,

		String status,

		List<OrderDetailRequestDTO> orderDetailRequestDTOList

) {

	public Order toEntity() {

		return Order.builder()
				.totalPrice(totalPrice)
				.destinationAddr(destinationAddr)
				.orderRequest(orderRequest)
				.isTakeOut(isTakeOut)
				.status(isTakeOut ? ORDER_COMPLETED : ORDER_PROGRESS)
				.build();
	}
}
