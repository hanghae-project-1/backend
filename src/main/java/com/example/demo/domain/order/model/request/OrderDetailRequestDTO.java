package com.example.demo.domain.order.model.request;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record OrderDetailRequestDTO(

		@NotBlank
		UUID menuId,

		@NotBlank
		Integer price,

		@NotBlank
		Integer quantity

) {

	public OrderDetail toEntity(Order order) {

		return OrderDetail.builder()
				.price(price)
				.quantity(quantity)
				.order(order)
				.menu(Menu.builder().id(menuId).build())
				.build();
	}

}
