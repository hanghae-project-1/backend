package com.example.demo.domain.order.model.response;

import java.util.List;

public record OrderResponseDTO(

		BaseOrderDTO baseOrder,

		String destinationAddr,

		String orderRequest,

		List<OrderDetailResponseDTO> orderDetailList

) {

}
