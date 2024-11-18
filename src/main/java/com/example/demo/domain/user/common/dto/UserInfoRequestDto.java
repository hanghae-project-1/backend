package com.example.demo.domain.user.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoRequestDto {

	@NotBlank
	@Size(min = 4, max = 10)
	@Pattern(regexp = "^[a-z0-9]+$", message = "아이디 확인하세요.")
	private String username;


	@NotBlank
	@Size(min = 8, max = 15, message = "아이디 및 패스워드 확인해주세요.")
	private String password;

}

