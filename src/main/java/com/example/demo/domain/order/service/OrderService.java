package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.domain.order.mapper.OrderMapper.toOrderDetailEntity;
import static com.example.demo.domain.order.mapper.OrderMapper.toOrderEntity;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	@Transactional
	public void createOrder(OrderRequestDTO request) {

		Order order = toOrderEntity(request);

		List<OrderDetail> orderDetails = request.orderDetailRequestDTOList().stream().map(requestDTO ->
				toOrderDetailEntity(order, requestDTO)
		).toList();

		order.addOrderDetail(orderDetails);

		orderRepository.save(order);
	}
}
