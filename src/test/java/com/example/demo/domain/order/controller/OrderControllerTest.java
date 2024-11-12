package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Nested
	@DisplayName("주문 생성 테스트")
	class createOrderTest {

		@Test
		@DisplayName("주문 생성 성공")
		void createOrder_SUCCESS() throws Exception {
			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					10000,
					"123 Main St",
					"Please deliver fast",
					true,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			// When & Then
			mockMvc.perform(post("/orders")
							.contentType("application/json")
							.content(objectMapper.writeValueAsString(requestDTO)))
					.andExpect(status().isCreated());
		}
	}
}