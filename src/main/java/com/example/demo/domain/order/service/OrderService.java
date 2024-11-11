package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public void createOrder(OrderRequestDTO request) {

		Order order = request.toEntity();

		List<OrderDetail> orderDetails = request.orderDetailRequestDTOList().stream().map(requestDTO ->
				requestDTO.toEntity(order)
		).toList();

		order.addOrderDetail(orderDetails);

		orderRepository.save(order);
	}
}
