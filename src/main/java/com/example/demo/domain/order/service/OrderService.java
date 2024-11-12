package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.order.exception.IncorrectTotalPriceException;
import com.example.demo.domain.order.exception.NotFoundOrderException;
import com.example.demo.domain.order.exception.ReturnPeriodPassedException;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper orderMapper;
	private final OrderRepository orderRepository;

	@Transactional
	public void createOrder(OrderRequestDTO request) {

		int sum = request.orderDetailRequestDTOList().stream().mapToInt(orderDetail -> orderDetail.price() * orderDetail.quantity()).sum();

		if (sum != request.totalPrice()) {
			throw new IncorrectTotalPriceException();
		}

		Order order = orderMapper.toOrderEntity(request);

		List<OrderDetail> orderDetails = request.orderDetailRequestDTOList().stream().map(requestDTO ->
				orderMapper.toOrderDetailEntity(order, requestDTO)
		).toList();

		order.addOrderDetail(orderDetails);

		orderRepository.save(order);
	}

	@Transactional
	public void modifyOrderStatus(UUID orderId) {

		Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);

		if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
			throw new ReturnPeriodPassedException();
		}

		//TODO: (준석) deleteBy 추가하기
		order.markAsDelete();

		orderRepository.save(order);
	}

}
