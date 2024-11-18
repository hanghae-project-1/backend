package com.example.demo.domain.user.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.controller.docs.LoginControllerDocs;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {
		@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		public Response<Void> login(
			@RequestParam("username") String username,
			@RequestParam("password") String password
		) {
			return Response.<Void>builder()
				.code(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase())
				.build();
		}

}
