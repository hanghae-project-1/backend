package com.example.demo.domain.order.model.response;

import java.util.List;

public record StoreOrderResponseDTO(

		Integer periodOrderCount,

		Integer periodTotalPrice,

		List<BaseOrderDTO> orderList
) {
}
