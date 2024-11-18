package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.model.request.OrderDetailRequestDTO;
import com.example.demo.domain.order.model.request.OrderRequestDTO;
import com.example.demo.domain.order.service.OrderService;
import com.example.demo.domain.user.common.entity.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

	@Value("${spring.jwt.secret}")
	String secret;

	private SecretKey secretKey;

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OrderService orderService;
	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		MockitoAnnotations.openMocks(this);
	}

	private String generateTestJwtTokenByCUSTOMER() {
		return Jwts.builder()
				.claim("username", "hello")
				.claim("role", Role.CUSTOMER)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000L)))
				.signWith(secretKey)
				.compact();
	}

	private String generateTestJwtTokenByOWNER() {
		return Jwts.builder()
				.claim("username", "hello")
				.claim("role", Role.OWNER)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000L)))
				.signWith(secretKey)
				.compact();
	}

	private String generateTestJwtTokenByMANAGER() {
		return Jwts.builder()
				.claim("username", "hello")
				.claim("role", Role.MANAGER)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000L)))
				.signWith(secretKey)
				.compact();
	}

	private String generateTestJwtTokenByMASTER() {
		return Jwts.builder()
				.claim("username", "hello")
				.claim("role", Role.MASTER)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000L)))
				.signWith(secretKey)
				.compact();
	}

	private OrderRequestDTO createOrderRequestDTO() {
		return new OrderRequestDTO(
				10000,
				"123 Main St",
				"Please deliver fast",
				true,
				"ORDER_COMPLETED",
				List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
		);
	}

	@Nested
	@DisplayName("주문 생성 테스트")
	class createOrderTest {

		@Test
		@DisplayName("주문 생성 성공")
		void createOrder_SUCCESS() throws Exception {
			// Given
			OrderRequestDTO requestDTO = createOrderRequestDTO();

			// When & Then
			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(requestDTO))
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isOk());
		}

		@Test
		@DisplayName("주문 생성 실패 _ 총 가격 정보 없음")
		void createOrder_FAIL_TOTAL_PRICE_IS_NULL() throws Exception {
			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					null,
					"123 Main St",
					"Please deliver fast",
					true,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			// When & Then
			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(requestDTO))
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isBadRequest())
					.andExpect(content().string("{\"code\":400,\"message\":\"총 가격을 입력해주세요.\"}"));
		}

		@Test
		@DisplayName("주문 생성 실패 _ 도착지 정보 없음")
		void createOrder_FAIL_DESTINATION_ADDR_IS_NULL() throws Exception {
			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					10000,
					null,
					"Please deliver fast",
					true,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			// When & Then
			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(requestDTO))
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isBadRequest())
					.andExpect(content().string("{\"code\":400,\"message\":\"도착지를 입력해주세요.\"}"));
		}

		@Test
		@DisplayName("주문 생성 실패 _ 포장 여부 정보 없음")
		void createOrder_FAIL_IS_TAKE_OUT_IS_NULL() throws Exception {
			// Given
			OrderRequestDTO requestDTO = new OrderRequestDTO(
					10000,
					"123 Main St",
					"Please deliver fast",
					null,
					"ORDER_COMPLETED",
					List.of(new OrderDetailRequestDTO(UUID.randomUUID(), 5000, 2))
			);

			// When & Then
			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(requestDTO))
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isBadRequest())
					.andExpect(content().string("{\"code\":400,\"message\":\"포장 여부를 입력해주세요.\"}"));
		}

		@Test
		@DisplayName("인증 실패 테스트")
		void createOrderAuthenticationFailure() throws Exception {
			OrderRequestDTO request = createOrderRequestDTO();

			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request)))
					.andExpect(status().isUnauthorized());
		}

		@Test
		@DisplayName("인가 실패 테스트 _ OWNER")
		void createOrderAuthorizationFailure_OWNER() throws Exception {
			OrderRequestDTO request = createOrderRequestDTO();

			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request))
							.header("Authorization", "Bearer " + generateTestJwtTokenByOWNER()))
					.andExpect(status().isForbidden());
		}

		@Test
		@DisplayName("인가 실패 테스트 _ MANAGER")
		void createOrderAuthorizationFailure_MANAGER() throws Exception {
			OrderRequestDTO request = createOrderRequestDTO();

			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request))
							.header("Authorization", "Bearer " + generateTestJwtTokenByMANAGER()))
					.andExpect(status().isForbidden());
		}

		@Test
		@DisplayName("인가 실패 테스트 _ MASTER")
		void createOrderAuthorizationFailure_MASTER() throws Exception {
			OrderRequestDTO request = createOrderRequestDTO();

			mockMvc.perform(post("/api/v1/order")
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(request))
							.header("Authorization", "Bearer " + generateTestJwtTokenByMASTER()))
					.andExpect(status().isForbidden());
		}

	}

	@Nested
	@DisplayName("주문 수정 테스트")
	class modifyOrderStatusTest {

		@Test
		@DisplayName("주문 상태 수정 성공 테스트 _ CUSTOMER")
		void modifyOrderStatus_Success_CUSTOMER() throws Exception {
			// Given
			UUID orderId = UUID.randomUUID();
			boolean isCancel = true;

			// When & Then
			mockMvc.perform(patch("/api/v1/order/{orderId}", orderId)
							.param("isCancel", String.valueOf(isCancel))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isOk());

			verify(orderService).modifyOrderStatus(orderId, isCancel);
		}

		@Test
		@DisplayName("주문 상태 수정 성공 테스트 _ MANAGER")
		void modifyOrderStatus_Success_MANAGER() throws Exception {
			// Given
			UUID orderId = UUID.randomUUID();
			boolean isCancel = true;

			// When & Then
			mockMvc.perform(patch("/api/v1/order/{orderId}", orderId)
							.param("isCancel", String.valueOf(isCancel))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMANAGER()))
					.andExpect(status().isOk());

			verify(orderService).modifyOrderStatus(orderId, isCancel);
		}

		@Test
		@DisplayName("주문 상태 수정 성공 테스트 _ MASTER")
		void modifyOrderStatus_Success_MASTER() throws Exception {
			// Given
			UUID orderId = UUID.randomUUID();
			boolean isCancel = true;

			// When & Then
			mockMvc.perform(patch("/api/v1/order/{orderId}", orderId)
							.param("isCancel", String.valueOf(isCancel))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMASTER()))
					.andExpect(status().isOk());

			verify(orderService).modifyOrderStatus(orderId, isCancel);
		}

		@Test
		@DisplayName("주문 상태 수정 실패 테스트 _ OWNER 실행")
		void modifyOrderStatus_FAIL_OWNER() throws Exception {
			// Given
			UUID orderId = UUID.randomUUID();
			boolean isCancel = true;

			// When & Then
			mockMvc.perform(patch("/api/v1/order/{orderId}", orderId)
							.param("isCancel", String.valueOf(isCancel))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByOWNER()))
					.andExpect(status().isForbidden());
		}

	}

	@Nested
	@DisplayName("주문 조회 테스트")
	class getOrder {

		@Test
		@DisplayName("주문 조회 성공 테스트 _ CUSTOMER")
		void getOrderDetails_Success_CUSTOMER() throws Exception {

			// Given
			UUID orderId = UUID.randomUUID();

			// When & Then
			mockMvc.perform(get("/api/v1/order/{orderId}", orderId)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isOk());

			verify(orderService).getOrderDetails(orderId);
		}

		@Test
		@DisplayName("주문 조회 실패 테스트 _ OWNER")
		void getOrderDetails_FAIL_OWNER() throws Exception {

			// Given
			UUID orderId = UUID.randomUUID();

			// When & Then
			mockMvc.perform(get("/api/v1/order/{orderId}", orderId)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByOWNER()))
					.andExpect(status().isForbidden());
		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ MANAGER")
		void getOrderDetails_Success_MANAGER() throws Exception {

			// Given
			UUID orderId = UUID.randomUUID();

			// When & Then
			mockMvc.perform(get("/api/v1/order/{orderId}", orderId)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMANAGER()))
					.andExpect(status().isOk());

			verify(orderService).getOrderDetails(orderId);
		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ MASTER")
		void getOrderDetails_Success_MASTER() throws Exception {

			// Given
			UUID orderId = UUID.randomUUID();

			// When & Then
			mockMvc.perform(get("/api/v1/order/{orderId}", orderId)
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMASTER()))
					.andExpect(status().isOk());

			verify(orderService).getOrderDetails(orderId);
		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ CUSTOMER")
		void getAllOrdersByCustomer_Success_CUSTOMER() throws Exception {

			// Given

			// When & Then
			mockMvc.perform(get("/api/v1/order/user")
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isOk());

			verify(orderService).getAllOrdersByCustomer();
		}

		@Test
		@DisplayName("주문 조회 실패 테스트 _ OWNER")
		void getAllOrdersByCustomer_FAIL_OWNER() throws Exception {

			// Given

			// When & Then
			mockMvc.perform(get("/api/v1/order/user")
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByOWNER()))
					.andExpect(status().isForbidden());

		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ MANAGER")
		void getAllOrdersByCustomer_Success_MANAGER() throws Exception {

			// Given

			// When & Then
			mockMvc.perform(get("/api/v1/order/user")
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMANAGER()))
					.andExpect(status().isOk());

			verify(orderService).getAllOrdersByCustomer();
		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ MASTER")
		void getAllOrdersByCustomer_Success_MASTER() throws Exception {

			// Given

			// When & Then
			mockMvc.perform(get("/api/v1/order/user")
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByMASTER()))
					.andExpect(status().isOk());

			verify(orderService).getAllOrdersByCustomer();
		}

		@Test
		@DisplayName("주문 조회 실패 테스트 _ CUSTOMER")
		void getAllOrdersByStore_FAIL_CUSTOMER() throws Exception {

			// Given
			UUID storeId = UUID.randomUUID();
			LocalDateTime startDate = LocalDateTime.now().minusDays(7);
			LocalDateTime endDate = LocalDateTime.now();

			// When & Then
			mockMvc.perform(get("/api/v1/order/store/{storeId}", storeId)
							.param("startDate", String.valueOf(startDate))
							.param("endDate", String.valueOf(endDate))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isForbidden());

			verify(orderService).getAllOrdersByStore(storeId, startDate, endDate);
		}

		@Test
		@DisplayName("주문 조회 성공 테스트 _ OWNER")
		void getAllOrdersByStore_SUCCESS_OWNER() throws Exception {

			// Given
			UUID storeId = UUID.randomUUID();
			LocalDateTime startDate = LocalDateTime.now().minusDays(7);
			LocalDateTime endDate = LocalDateTime.now();

			// When & Then
			mockMvc.perform(get("/api/v1/order/store/{storeId}", storeId)
							.param("startDate", String.valueOf(startDate))
							.param("endDate", String.valueOf(endDate))
							.contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", "Bearer " + generateTestJwtTokenByCUSTOMER()))
					.andExpect(status().isForbidden());

			verify(orderService).getAllOrdersByStore(storeId, startDate, endDate);
		}


	}

}
