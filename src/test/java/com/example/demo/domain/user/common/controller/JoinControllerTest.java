package com.example.demo.domain.user.common.controller;

import com.example.demo.domain.user.common.dto.JoinRequestDto;
import com.example.demo.domain.user.common.service.JoinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JoinController.class)
@AutoConfigureMockMvc(addFilters = false)
class JoinControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JoinService joinService;
	@Test
	@DisplayName("고객-유효한 데이터로 회원가입 성공")
	void JoinProcessWithValidData() throws Exception {
		mockMvc.perform(post("/api/v1/join")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", "testuser")
				.param("password", "password123"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(201))
			.andExpect(jsonPath("$.message").value("Created"));
	}

	@Test
	@DisplayName("고객-유효하지 않은 비밀번호로 회원가입 실패")
	void JoinProcessWithInvalidPassword() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("testuser", "pass");

		mockMvc.perform(post("/api/v1/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("고객-유효하지 않은 사용자명 패턴으로 회원가입 실패")
	void JoinProcessWithInvalidUsernamePattern() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("Test123", "password123");

		mockMvc.perform(post("/api/v1/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}


	@Test
	@DisplayName("사장-유효한 데이터로 회원가입 성공")
	void OwnerJoinProcessWithValidData() throws Exception {
		mockMvc.perform(post("/api/v1/owner/join")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", "testuser")
				.param("password", "password123"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(201))
			.andExpect(jsonPath("$.message").value("Created"));
	}

	@Test
	@DisplayName("사장-유효하지 않은 비밀번호로 회원가입 실패")
	void OwnerJoinProcessWithInvalidPassword() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("testuser", "pass");

		mockMvc.perform(post("/api/v1/owner/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("사장-유효하지 않은 사용자명 패턴으로 회원가입 실패")
	void OwnerJoinProcessWithInvalidUsernamePattern() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("Test123", "password123");

		mockMvc.perform(post("/api/v1/owner/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("관리자-유효한 데이터로 회원가입 성공")
	void ManagerOwnerJoinProcessWithValidData() throws Exception {
		mockMvc.perform(post("/api/v1/manager/join")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", "testuser")
				.param("password", "password123"))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(201))
			.andExpect(jsonPath("$.message").value("Created"));
	}

	@Test
	@DisplayName("관리자-유효하지 않은 비밀번호로 회원가입 실패")
	void ManagerJoinProcessWithInvalidPassword() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("testuser", "pass");

		mockMvc.perform(post("/api/v1/manager/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("관리자-유효하지 않은 사용자명 패턴으로 회원가입 실패")
	void ManagerJoinProcessWithInvalidUsernamePattern() throws Exception {
		JoinRequestDto dto = new JoinRequestDto("Test123", "password123");

		mockMvc.perform(post("/api/v1/manager/join") // URL 수정
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
	}
	
}