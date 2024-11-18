package com.example.demo.domain.user.common.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("로그인 요청이 성공 할 경우")
	void LoginSuccsse() throws Exception {
		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", "testuser")
				.param("password", "password123"))
			.andExpect(status().isOk());
	}

}