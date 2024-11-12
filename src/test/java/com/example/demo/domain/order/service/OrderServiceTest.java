package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OrderServiceTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderService orderService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Nested
	@DisplayName("주문 생성 테스트")
	class createOrderTest {

		@Test
		@DisplayName("주문 생성 성공")
		void createOrder_SUCCESS() {
			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					10000,
					"123 Main St",
					"Please deliver fast",
					true,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			Order order = Order.builder()
					.totalPrice(requestDTO.totalPrice())
					.destinationAddr(requestDTO.destinationAddr())
					.orderRequest(requestDTO.orderRequest())
					.isTakeOut(requestDTO.isTakeOut())
					.status(Status.Order.valueOf(requestDTO.status()))
					.build();

			List<OrderDetail> orderDetails = requestDTO.orderDetailRequestDTOList().stream().map(dto ->
					OrderMapper.toOrderDetailEntity(order, dto)
			).toList();

			order.addOrderDetail(orderDetails);

			when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
				Order savedOrder = invocation.getArgument(0);
				savedOrder.addOrderDetail(orderDetails);
				return savedOrder;
			});

			// When
			orderService.createOrder(requestDTO);

			// Then
			ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
			verify(orderRepository, times(1)).save(orderCaptor.capture());
			Order savedOrder = orderCaptor.getValue();

			assertThat(savedOrder.getTotalPrice()).isEqualTo(10000);
			assertThat(savedOrder.getDestinationAddr()).isEqualTo("123 Main St");
			assertThat(savedOrder.getOrderRequest()).isEqualTo("Please deliver fast");
			assertThat(savedOrder.getIsTakeOut()).isTrue();
			assertThat(savedOrder.getStatus()).isEqualTo(Status.Order.valueOf("ORDER_COMPLETED"));
			assertThat(savedOrder.getOrderDetailList()).hasSize(1);
			assertThat(savedOrder.getOrderDetailList().get(0).getPrice()).isEqualTo(5000);
			assertThat(savedOrder.getOrderDetailList().get(0).getQuantity()).isEqualTo(2);
		}
	}
}
