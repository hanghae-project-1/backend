package com.example.demo.domain.user.common.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.user.common.dto.JoinRequestDto;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.DuplicateUsernameExistsException;
import com.example.demo.domain.user.common.exception.NotPoundUriException;
import com.example.demo.domain.user.common.mapper.UserMapper;
import com.example.demo.domain.user.common.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JoinService {

	private final UserRepository userRepository;
	private final UserValidService userValidService;
	private final UserMapper userMapper;

	public void joinProcess(JoinRequestDto dto) {
		if (userValidService.userNameCheck(dto.getUsername())) {
			throw new DuplicateUsernameExistsException();
		}

		User customerUser = userMapper.toCustomer(dto);
		userRepository.save(customerUser);
	}

	public void joinProcess(JoinRequestDto dto, String role) {
		if (userValidService.userNameCheck(dto.getUsername())) {
			throw new DuplicateUsernameExistsException();
		}

		User user = switch (role) {
			case "owner" -> userMapper.toOwner(dto, role);
			case "manager" -> userMapper.toManager(dto, role);
			case "master" -> userMapper.toMaster(dto, role);
			default -> throw new NotPoundUriException();
		};
		userRepository.save(user);
	}

}
