package com.example.demo.domain.user.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.controller.docs.JoinControllerDocs;
import com.example.demo.domain.user.common.dto.JoinRequestDto;
import com.example.demo.domain.user.common.service.JoinService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class JoinController implements JoinControllerDocs {

	private final JoinService joinService;

	@PostMapping("/join")
	public Response<Void> joinProcess(@Valid JoinRequestDto dto) {
		joinService.joinProcess(dto);
		return Response.<Void>builder()
			.code(HttpStatus.CREATED.value())
			.message(HttpStatus.CREATED.getReasonPhrase())
			.build();
	}

	@PostMapping("/{role}/join")
	public Response<Void> joinProcess(
		@Valid JoinRequestDto dto, @PathVariable String role) {
		joinService.joinProcess(dto, role);
		return Response.<Void>builder()
			.code(HttpStatus.CREATED.value())
			.message(HttpStatus.CREATED.getReasonPhrase())
			.build();
	}
}
