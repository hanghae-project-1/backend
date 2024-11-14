package com.example.demo.domain.order.mapper;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderDetail;
import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.model.response.BaseOrderDTO;
import com.example.demo.domain.order.model.response.OrderDetailResponseDTO;
import com.example.demo.domain.order.model.response.OrderResponseDTO;
import com.example.demo.domain.order.model.response.StoreOrderResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;
import static com.example.demo.domain.entity.common.Status.Order.ORDER_PROGRESS;

@Component
public class OrderMapper {

	public OrderDetail toOrderDetailEntity(Order order, OrderDetailRequestDTO requestDTO) {

		return OrderDetail.builder()
				.price(requestDTO.price())
				.quantity(requestDTO.quantity())
				.order(order)
				.menu(Menu.builder().id(requestDTO.menuId()).build())
				.build();
	}

	public Order toOrderEntity(OrderRequestDTO requestDTO) {

		return Order.builder()
				.totalPrice(requestDTO.totalPrice())
				.destinationAddr(requestDTO.destinationAddr())
				.orderRequest(requestDTO.orderRequest())
				.isTakeOut(requestDTO.isTakeOut())
				.status(requestDTO.isTakeOut() ? ORDER_COMPLETED : ORDER_PROGRESS)
				.build();
	}

	public OrderResponseDTO toOrderResponseDTO(Order order) {

		return new OrderResponseDTO(
				new BaseOrderDTO(
						order.getId(),
						order.getTotalPrice(),
						order.getIsTakeOut(),
						order.getStatus().name()
				),
				order.getDestinationAddr(),
				order.getOrderRequest(),
				order.getOrderDetailList().stream().map(orderDetail ->
						new OrderDetailResponseDTO(
								orderDetail.getMenu().getName(),
								orderDetail.getPrice(),
								orderDetail.getQuantity()
						)
				).toList()
		);
	}

	public StoreOrderResponseDTO toStoreOrderResponseDTO(List<Order> orderList) {

		return new StoreOrderResponseDTO(
				orderList.size(),
				orderList.stream().mapToInt(Order::getTotalPrice).sum(),
				orderList.stream().map(order ->
						new BaseOrderDTO(
								order.getId(),
								order.getTotalPrice(),
								order.getIsTakeOut(),
								order.getStatus().name()
						)
				).toList()
		);
	}
}
