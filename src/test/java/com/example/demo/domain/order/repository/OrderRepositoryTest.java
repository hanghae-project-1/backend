package com.example.demo.domain.order.repository;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.order.entity.OrderDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("dev")
class OrderRepositoryTest {

	@Autowired
	private OrderRepository orderRepository;

	@Nested
	@DisplayName("주문 생성 테스트")
	class createOrderTest {

		@Test
		@DisplayName("주문 생성 성공")
		void createOrder_SUCCESS() {
			// Given
			Order order = Order.builder()
					.totalPrice(10000)
					.destinationAddr("123 Main St")
					.orderRequest("Please deliver fast")
					.isTakeOut(true)
					.status(Status.Order.valueOf("ORDER_COMPLETED"))
					.build();

			OrderDetail orderDetail = OrderDetail.builder()
					.price(5000)
					.quantity(2)
					.order(order)
					.menu(Menu.builder().id(UUID.randomUUID()).build())
					.build();

			order.addOrderDetail(List.of(orderDetail));

			// When
			Order savedOrder = orderRepository.save(order);

			// Then
			assertThat(savedOrder).isNotNull();
			assertThat(savedOrder.getOrderDetailList()).hasSize(1);
		}
	}
}
