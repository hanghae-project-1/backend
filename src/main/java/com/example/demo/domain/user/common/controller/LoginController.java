package com.example.demo.domain.user.common.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.common.controller.docs.LoginControllerDocs;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {
		@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		public ResponseEntity<?> login(
			@RequestParam("username") String username,
			@RequestParam("password") String password
		) {
			throw new UnsupportedOperationException("필터에서 response 처리");
		}

}
