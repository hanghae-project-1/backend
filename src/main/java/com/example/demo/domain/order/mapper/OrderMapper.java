package com.example.demo.domain.order.mapper;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import org.springframework.stereotype.Component;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;
import static com.example.demo.domain.entity.common.Status.Order.ORDER_PROGRESS;

@Component
public class OrderMapper {

	public Order toOrderEntity(OrderRequestDTO requestDTO) {

		return Order.builder()
				.totalPrice(requestDTO.totalPrice())
				.destinationAddr(requestDTO.destinationAddr())
				.orderRequest(requestDTO.orderRequest())
				.isTakeOut(requestDTO.isTakeOut())
				.status(requestDTO.isTakeOut() ? ORDER_COMPLETED : ORDER_PROGRESS)
				.build();
	}

	public OrderDetail toOrderDetailEntity(Order order, OrderDetailRequestDTO requestDTO) {

		return OrderDetail.builder()
				.price(requestDTO.price())
				.quantity(requestDTO.quantity())
				.order(order)
				.menu(Menu.builder().id(requestDTO.menuId()).build())
				.build();
	}

}
