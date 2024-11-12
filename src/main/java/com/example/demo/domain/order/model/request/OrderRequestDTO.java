package com.example.demo.domain.order.model.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

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
}
