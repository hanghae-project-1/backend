package com.example.demo.domain.order.service;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.store.entity.Store;
import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderDetail;
import com.example.demo.domain.order.exception.IncorrectTotalPriceException;
import com.example.demo.domain.order.exception.IsNotYourOrderException;
import com.example.demo.domain.order.exception.ReturnPeriodPassedException;
import com.example.demo.domain.order.mapper.OrderMapper;
import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.model.response.BaseOrderDTO;
import com.example.demo.domain.order.model.response.OrderDetailResponseDTO;
import com.example.demo.domain.order.model.response.OrderResponseDTO;
import com.example.demo.domain.order.model.response.StoreOrderResponseDTO;
import com.example.demo.domain.order.repository.OrderRepository;
import com.example.demo.domain.order.repository.StoreRepository;
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

import static com.example.demo.domain.entity.common.Status.Order.ORDER_COMPLETED;
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
	private StoreRepository storeRepository;

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
		@DisplayName("주문 수정 테스트 _ 주문 접수 성공")
		void modifyOrderStatusTest_ORDER_SUCCESS() {

			// Given
			UUID orderId = UUID.randomUUID();
			Order mockOrder = createMockOrder(orderId, LocalDateTime.now().minusMinutes(3));

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
			when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

			// When
			orderService.modifyOrderStatus(orderId, false);

			// Then
			assertFalse(mockOrder.getIsDelete());
			assertTrue(mockOrder.getIsPublic());
			assertNull(mockOrder.getDeletedAt());
			verify(orderRepository).save(mockOrder);

		}

		@Test
		@DisplayName("주문 수정 테스트 _ 취소 성공")
		void modifyOrderStatusTest_CANCEL_SUCCESS() {

			// Given
			UUID orderId = UUID.randomUUID();
			Order mockOrder = createMockOrder(orderId, LocalDateTime.now().minusMinutes(3));

			when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
			when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

			// When
			orderService.modifyOrderStatus(orderId, true);

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
					() -> orderService.modifyOrderStatus(orderId, true)
			);
			assertEquals(400, exception.getHttpStatus().value());

		}
	}

	@Nested
	@DisplayName("주문 조회 테스트")
	class getOrderDetailsTest {

		private Order createMockOrder(UUID id, UUID userId, UUID menuId) {
			Order order = new Order();
			OrderDetail orderDetail = new OrderDetail();
			Menu menu = new Menu();

			ReflectionTestUtils.setField(order, "id", id);
			ReflectionTestUtils.setField(order, "createdBy", userId);
			ReflectionTestUtils.setField(menu, "id", menuId);
			ReflectionTestUtils.setField(menu, "name", "음식");
			ReflectionTestUtils.setField(orderDetail, "menu", menu);
			ReflectionTestUtils.setField(order, "orderDetailList", List.of(orderDetail));

			return order;
		}

		private Order createMockStoreOrder(UUID storeId, UUID orderId, UUID userId) {

			Store store = new Store();
			Order order = new Order();

			ReflectionTestUtils.setField(store, "id", storeId);
			ReflectionTestUtils.setField(store, "createdBy", userId);
			ReflectionTestUtils.setField(order, "id", orderId);
			ReflectionTestUtils.setField(order, "totalPrice", 10000);
			ReflectionTestUtils.setField(order, "store", store);

			return order;
		}

		@Test
		@DisplayName("단건 상세조회 성공")
		void getOrderDetails_SUCCESS() {

			// Given
			UUID orderId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			UUID menuId = UUID.randomUUID();

			Order mockOrder = createMockOrder(orderId, userId, menuId);

			when(orderRepository.getOrderWithFullDetails(orderId)).thenReturn(mockOrder);
			when(orderMapper.toOrderResponseDTO(mockOrder)).thenReturn(new OrderResponseDTO(
					new BaseOrderDTO(
							mockOrder.getId(),
							mockOrder.getTotalPrice(),
							mockOrder.getIsTakeOut(),
							""
					),
					mockOrder.getDestinationAddr(),
					mockOrder.getOrderRequest(),
					mockOrder.getOrderDetailList().stream().map(orderDetail ->
							new OrderDetailResponseDTO(
									orderDetail.getMenu().getName(),
									orderDetail.getPrice(),
									orderDetail.getQuantity()
							)
					).toList()
			));

			// When
			OrderResponseDTO orderResponseDTO = orderService.getOrderDetails(orderId, userId);

			// Then
			assertThat(orderResponseDTO.baseOrder().id()).isEqualTo(orderId);
		}

		@Test
		@DisplayName("단건 상세조회 실패 _ 주문자와 조회자가 다름")
		void getOrderDetails_FAIL() {

			// Given
			UUID orderId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			UUID menuId = UUID.randomUUID();

			Order mockOrder = createMockOrder(orderId, UUID.randomUUID(), menuId);

			when(orderRepository.getOrderWithFullDetails(orderId)).thenReturn(mockOrder);

			// When & Then
			assertThrows(
					IsNotYourOrderException.class,
					() -> orderService.getOrderDetails(orderId, userId)
			);

		}

		@Test
		@DisplayName("내 주문 조회 성공")
		void getOrdersByCustomer_SUCCESS() {

			// Given
			UUID userId = UUID.randomUUID();
			UUID menuId = UUID.randomUUID();

			Order mockOrder = createMockOrder(UUID.randomUUID(), userId, menuId);

			when(orderRepository.getOrdersWithFullDetails(userId)).thenReturn(List.of(mockOrder));
			when(orderMapper.toOrderResponseDTO(mockOrder)).thenReturn(new OrderResponseDTO(
					new BaseOrderDTO(
							mockOrder.getId(),
							mockOrder.getTotalPrice(),
							mockOrder.getIsTakeOut(),
							""
					),
					mockOrder.getDestinationAddr(),
					mockOrder.getOrderRequest(),
					mockOrder.getOrderDetailList().stream().map(orderDetail ->
							new OrderDetailResponseDTO(
									orderDetail.getMenu().getName(),
									orderDetail.getPrice(),
									orderDetail.getQuantity()
							)
					).toList()
			));

			// When
			List<OrderResponseDTO> orderResponseDTOList = orderService.getAllOrdersByCustomer(userId);

			// Then
			assertThat(orderResponseDTOList).hasSize(1);
			assertThat(orderResponseDTOList.get(0).baseOrder().id()).isEqualTo(mockOrder.getId());
		}

		@Test
		@DisplayName("내 주문 조회 실패 _ 주문자와 조회자가 다름")
		void getOrdersByCustomer_FAIL() {

			// Given
			UUID userId = UUID.randomUUID();
			UUID menuId = UUID.randomUUID();

			Order mockOrder = createMockOrder(UUID.randomUUID(), UUID.randomUUID(), menuId);

			when(orderRepository.getOrdersWithFullDetails(userId)).thenReturn(List.of(mockOrder));

			// When & Then
			assertThrows(
					IsNotYourOrderException.class,
					() -> orderService.getAllOrdersByCustomer(userId)
			);

		}

		@Test
		@DisplayName("가게 주문 조회 성공")
		void getAllOrdersByStore_SUCCESS() {

			// Given
			UUID storeId = UUID.randomUUID();
			UUID orderId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			LocalDateTime endDate = LocalDateTime.now();
			LocalDateTime startDate = endDate.minusDays(7);

			Order mockStoreOrder = createMockStoreOrder(storeId, orderId, userId);

			when(storeRepository.findById(storeId)).thenReturn(Optional.of(mockStoreOrder.getStore()));
			when(orderRepository.findAllByStoreIdAndCreatedAtBetween(storeId, startDate, endDate)).thenReturn(List.of(mockStoreOrder));
			when(orderMapper.toStoreOrderResponseDTO(List.of(mockStoreOrder))).thenReturn(new StoreOrderResponseDTO(
					1,
					10000,
					List.of(new BaseOrderDTO(
							mockStoreOrder.getId(),
							mockStoreOrder.getTotalPrice(),
							false,
							ORDER_COMPLETED.name()
					))
			));

			// When
			StoreOrderResponseDTO storeOrderResponseDTO = orderService.getAllOrdersByStore(storeId, startDate, endDate, userId);

			// Then
			assertThat(storeOrderResponseDTO.periodOrderCount()).isEqualTo(1);
			assertThat(storeOrderResponseDTO.periodTotalPrice()).isEqualTo(10000);
			assertThat(storeOrderResponseDTO.orderList()).hasSize(1);
			assertThat(storeOrderResponseDTO.orderList().get(0).id()).isEqualTo(orderId);
		}

		@Test
		@DisplayName("가게 주문 조회 실패 _ 내 가게가 아님")
		void getAllOrdersByStore_FAIL() {

			// Given
			UUID storeId = UUID.randomUUID();
			UUID orderId = UUID.randomUUID();
			UUID userId = UUID.randomUUID();
			LocalDateTime endDate = LocalDateTime.now();
			LocalDateTime startDate = endDate.minusDays(7);

			Order mockStoreOrder = createMockStoreOrder(storeId, orderId, userId);

			when(storeRepository.findById(storeId)).thenReturn(Optional.of(mockStoreOrder.getStore()));

			// When & Then
			assertThrows(
					IllegalArgumentException.class,
					() -> orderService.getAllOrdersByStore(storeId, startDate, endDate, UUID.randomUUID())
			);

		}
	}

}
