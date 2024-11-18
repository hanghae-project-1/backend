package com.example.demo.domain.user.common.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("회원가입, 로그인, 회원탈퇴 통합 테스트")
	public void testUserRegistrationLoginAndDeletion() throws Exception {
		String username = "testuser5";
		String password = "password123";

		// 1. 회원가입
		mockMvc.perform(post("/api/v1/join")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", username)
				.param("password", password))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.code").value(201))
			.andExpect(jsonPath("$.message").value("Created"));

		// 2. 로그인
		MvcResult loginResult = mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.param("username", username)
				.param("password", password))
			.andExpect(status().isOk())
			.andExpect(header().exists("Authorization"))
			.andExpect(jsonPath("$.code").value(200))
			.andExpect(jsonPath("$.message").value("로그인이 성공했습니다."))
			.andReturn();

		String token = loginResult.getResponse().getHeader("Authorization");
		token = token.substring(7);

		// 3. 회원 탈퇴
		String userJson = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

		mockMvc.perform(delete("/api/v1/user/delete")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson))
			.andExpect(status().isOk());
	}

}