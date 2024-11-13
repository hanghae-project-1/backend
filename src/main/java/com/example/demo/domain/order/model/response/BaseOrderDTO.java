package com.example.demo.domain.order.model.response;

import java.util.UUID;

public record BaseOrderDTO(

		UUID id,
		Integer totalPrice,
		Boolean isTakeOut,
		String status

) {
}
