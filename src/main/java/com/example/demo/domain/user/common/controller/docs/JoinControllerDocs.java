package com.example.demo.domain.user.common.controller.docs;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.dto.JoinRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Join", description = "회원가입 API")
public interface JoinControllerDocs {
	@Operation(summary = "고객 회원가입", description = "고객 회원가입 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = Response.class))),
		@ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/join")
	Response<Void> joinProcess(@Valid JoinRequestDto request);

	@Operation(summary = "사장, 관리자, 마스터 회원가입", description = "사장, 관리자, 마스터 회원가입 API 입니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = Response.class))),
		@ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = Response.class)))
	})
	@PostMapping("/api/v1/{role}/join")
	Response<Void> joinProcess(@Valid JoinRequestDto dto, @PathVariable String role);

}
