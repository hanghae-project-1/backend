package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Store;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderDetail;
import com.example.demo.domain.order.exception.IncorrectTotalPriceException;
import com.example.demo.domain.order.exception.IsNotYourOrderException;
import com.example.demo.domain.order.exception.NotFoundOrderException;
import com.example.demo.domain.order.exception.ReturnPeriodPassedException;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.model.response.OrderResponseDTO;
import com.example.demo.domain.order.model.response.StoreOrderResponseDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.order.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper orderMapper;
	private final OrderRepository orderRepository;
	private final StoreRepository storeRepository;

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
	public void modifyOrderStatus(UUID orderId, Boolean isCancel) {

		Order order = orderRepository.findById(orderId).orElseThrow(NotFoundOrderException::new);

		if (isCancel) {

			if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5))) {
				throw new ReturnPeriodPassedException();
			}

			//TODO: (준석) deleteBy 추가하기
			order.markAsDelete();

		} else {

			order.updateStatus(ORDER_COMPLETED);
		}

		orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public OrderResponseDTO getOrderDetails(UUID orderId, UUID userId) {

		Order order = orderRepository.getOrderWithFullDetails(orderId);

		if (!order.getCreatedBy().equals(userId)) {
			throw new IsNotYourOrderException();
		}

		return orderMapper.toOrderResponseDTO(order);
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getAllOrdersByCustomer(UUID userId) {

		List<Order> orderList = orderRepository.getOrdersWithFullDetails(userId);

		return orderList.stream().map(order -> {

			if (!order.getCreatedBy().equals(userId))
				throw new IsNotYourOrderException();

			return orderMapper.toOrderResponseDTO(order);
		}).toList();
	}

	@Transactional(readOnly = true)
	public StoreOrderResponseDTO getAllOrdersByStore(UUID storeId, LocalDateTime startDate, LocalDateTime endDate, UUID userId) {

		//TODO: (준석) store 추가되면 repo, exception 수정하기
		Store store = storeRepository.findById(storeId).orElseThrow();

		if (!store.getCreatedBy().equals(userId)) {
			throw new IllegalArgumentException();
		}

		List<Order> orderList = orderRepository.findAllByStoreIdAndCreatedAtBetween(storeId, startDate, endDate);

		return orderMapper.toStoreOrderResponseDTO(orderList);
	}
}
