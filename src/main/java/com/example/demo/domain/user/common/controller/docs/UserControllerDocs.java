package com.example.demo.domain.user.common.controller.docs;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.dto.PasswordChangeRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "USER", description = "비밀번호 변경 API")
public interface UserControllerDocs {

	@Operation(summary = "비밀번호 변경", description = "새로운 비밀번호를 받아 변경합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "비밀번호 변경 성공",
			content = @Content(schema = @Schema(implementation = Response.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청",
			content = @Content(schema = @Schema(implementation = Response.class))),
	})
	@PostMapping("/password")
	Response<Void> passwordChange(
		@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "비밀번호 변경 요청 데이터",
			required = true,
			content = @Content(schema = @Schema(implementation = PasswordChangeRequestDto.class))
		)
		@RequestBody PasswordChangeRequestDto dto
	);
}