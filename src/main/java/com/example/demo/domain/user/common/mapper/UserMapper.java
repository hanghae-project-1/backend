package com.example.demo.domain.user.common.mapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.domain.user.common.dto.JoinRequestDto;
import com.example.demo.domain.user.common.entity.Role;
import com.example.demo.domain.user.common.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMapper {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User toCustomer(JoinRequestDto dto) {
		return User.builder()
			.username(dto.getUsername())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.role(Role.CUSTOMER)
			.build();
	}

	public User toOwner(JoinRequestDto dto, String role) {
		return User.builder()
			.username(dto.getUsername())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.role(Role.valueOf(role.toUpperCase()))
			.build();
	}

	public User toManager(JoinRequestDto dto, String role) {
		return User.builder()
			.username(dto.getUsername())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.role(Role.valueOf(role.toUpperCase()))
			.build();
	}

	public User toMaster(JoinRequestDto dto, String role) {
		return User.builder()
			.username(dto.getUsername())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.role(Role.valueOf(role.toUpperCase()))
			.build();
	}
}
