package com.example.demo.domain.order.service;

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
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.store.exception.IsNotYourStoreException;
import com.example.demo.domain.store.exception.NotFoundStoreException;
import com.example.demo.domain.store.repository.StoreRepository;
import com.example.demo.domain.user.common.service.UserService;
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
	private final UserService userService;
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

			order.markAsDelete(userService.getCurrentUsername());

		} else {

			order.updateStatus(ORDER_COMPLETED);
		}

		orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public OrderResponseDTO getOrderDetails(UUID orderId) {

		Order order = orderRepository.getOrderWithFullDetails(orderId);
		String currentUsername = userService.getCurrentUsername();

		if (!order.getCreatedBy().equals(currentUsername)) {
			throw new IsNotYourOrderException();
		}

		return orderMapper.toOrderResponseDTO(order);
	}

	@Transactional(readOnly = true)
	public List<OrderResponseDTO> getAllOrdersByCustomer() {

		String currentUsername = userService.getCurrentUsername();

		List<Order> orderList = orderRepository.getOrdersWithFullDetails(currentUsername);

		return orderList.stream().map(order -> {

			if (!order.getCreatedBy().equals(currentUsername))
				throw new IsNotYourOrderException();

			return orderMapper.toOrderResponseDTO(order);
		}).toList();
	}

	@Transactional(readOnly = true)
	public StoreOrderResponseDTO getAllOrdersByStore(UUID storeId, LocalDateTime startDate, LocalDateTime endDate) {

		String currentUsername = userService.getCurrentUsername();
		String currentUserRole = userService.getCurrentUserRole();

		Store store = storeRepository.findById(storeId).orElseThrow(NotFoundStoreException::new);

		if (currentUserRole.equals("ROLE_OWNER") && !store.getCreatedBy().equals(currentUsername)) {
			throw new IsNotYourStoreException();
		}

		List<Order> orderList = orderRepository.findAllByStoreIdAndCreatedAtBetween(storeId, startDate, endDate);

		return orderMapper.toStoreOrderResponseDTO(orderList);
	}
}
