package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Order;
import com.example.demo.domain.entity.OrderDetail;
import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.exception.IncorrectTotalPriceException;
import com.example.demo.domain.order.exception.ReturnPeriodPassedException;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private OrderMapper orderMapper;

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

			Order mockOrder = Order.builder()
					.totalPrice(requestDTO.totalPrice())
					.destinationAddr(requestDTO.destinationAddr())
					.orderRequest(requestDTO.orderRequest())
					.isTakeOut(requestDTO.isTakeOut())
					.status(Status.Order.valueOf(requestDTO.status()))
					.build();

			OrderDetail mockOrderDetail = OrderDetail.builder()
					.price(5000)
					.quantity(2)
					.build();

			when(orderMapper.toOrderEntity(requestDTO)).thenReturn(mockOrder);
			when(orderMapper.toOrderDetailEntity(eq(mockOrder), any(OrderDetailRequestDTO.class))).thenReturn(mockOrderDetail);
			when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

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

		@Test
		@DisplayName("주문 생성 실패 _ 총 가격과 개별 가격이 다름")
		void createOrder_FAIL() {

			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					15000,
					"123 Main St",
					"Please deliver fast",
					true,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			// When & Then
			IncorrectTotalPriceException exception = assertThrows(
					IncorrectTotalPriceException.class,
					() -> orderService.createOrder(requestDTO)
			);
			assertEquals(400, exception.getHttpStatus().value());

		}

	}

	@Nested
	@DisplayName("주문 수정 테스트")
	class modifyOrderStatusTest {

		private Order createMockOrder(UUID id, LocalDateTime createdAt) {
			Order order = new Order();
			// Reflection을 사용하여 private 필드 설정
			ReflectionTestUtils.setField(order, "id", id);
			ReflectionTestUtils.setField(order, "createdAt", createdAt);
			return order;
		}

		@Test
		@DisplayName("주문 수정 테스트 _ 수정 성공")
		void modifyOrderStatusTest_SUCCESS() {

			// Given
			UUID orderId = UUID.randomUUID();
			Order mockOrder = createMockOrder(orderId, LocalDateTime.now().minusMinutes(3));

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
			when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

			// When
			orderService.modifyOrderStatus(orderId);

			// Then
			assertTrue(mockOrder.getIsDelete());
			assertFalse(mockOrder.getIsPublic());
			assertNotNull(mockOrder.getDeletedAt());
			verify(orderRepository).save(mockOrder);

		}

		@Test
		@DisplayName("주문 수정 테스트 _ 주문 생성 후 5분이 지나서 실패")
		void modifyOrderStatusTest_FAIL() {

			// Given
			UUID orderId = UUID.randomUUID();
			Order mockOrder = createMockOrder(orderId, LocalDateTime.now().minusMinutes(10));

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

			// When & Then
			ReturnPeriodPassedException exception = assertThrows(
					ReturnPeriodPassedException.class,
					() -> orderService.modifyOrderStatus(orderId)
			);
			assertEquals(400, exception.getHttpStatus().value());

		}
	}
}
