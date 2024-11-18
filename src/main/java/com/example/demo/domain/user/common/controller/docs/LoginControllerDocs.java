package com.example.demo.domain.user.common.controller.docs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.common.model.response.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "로그인", description = "로그인 API")
public interface LoginControllerDocs {

	@Operation(summary = "로그인", description = "사용자 이름과 비밀번호로 로그인합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그인 성공",
			content = @Content(schema = @Schema(implementation = Response.class))),
		@ApiResponse(responseCode = "401", description = "로그인 실패",
			content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	ResponseEntity<?> login(
		@Parameter(description = "사용자 이름", required = true)
		@RequestParam("username") String username,

		@Parameter(description = "비밀번호", required = true)
		@RequestParam("password") String password
	);
}