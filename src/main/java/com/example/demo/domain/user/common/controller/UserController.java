package com.example.demo.domain.user.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.controller.docs.UserControllerDocs;
import com.example.demo.domain.user.common.dto.PasswordChangeRequestDto;
import com.example.demo.domain.user.common.service.PasswordChangeService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerDocs {

	private final PasswordChangeService passwordChangeService;

	@PostMapping("/password")
	public Response<Void> passwordChange(@RequestBody PasswordChangeRequestDto dto){
		passwordChangeService.changePassword(dto);
		return Response .<Void>builder()
			.code(HttpStatus.OK.value())
			.message(HttpStatus.OK.getReasonPhrase())
			.build();
	}

}
