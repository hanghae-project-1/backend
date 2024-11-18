package com.example.demo.domain.user.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.model.response.Response;
import com.example.demo.domain.user.common.controller.docs.UserControllerDocs;
import com.example.demo.domain.user.common.dto.UserInfoRequestDto;
import com.example.demo.domain.user.common.service.UserWithdrawnService;
import com.example.demo.domain.user.common.service.PasswordChangeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerDocs {

	private final PasswordChangeService passwordChangeService;
	private final UserWithdrawnService deleteUserService;

	@PostMapping("/password")
	public Response<Void> passwordChange(@RequestBody UserInfoRequestDto dto){
		passwordChangeService.changePassword(dto);
		return Response .<Void>builder()
			.code(HttpStatus.OK.value())
			.message(HttpStatus.OK.getReasonPhrase())
			.build();
	}

	@DeleteMapping("/delete")
	public Response<Void> deleteUser(@RequestBody UserInfoRequestDto dto){
		deleteUserService.soft(dto);
		return Response .<Void>builder()
			.code(HttpStatus.OK.value())
			.message(HttpStatus.OK.getReasonPhrase())
			.build();
	}

}
