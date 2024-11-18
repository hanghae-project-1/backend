package com.example.demo.domain.user.common.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.common.dto.PasswordChangeRequestDto;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.NotPoundUserException;
import com.example.demo.domain.user.common.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordChangeService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserService userService;

	public void changePassword(PasswordChangeRequestDto dto) {
		String currentUsername = userService.getCurrentUsername();
		String changeUsername = dto.getUsername();

		if(!currentUsername.equals(changeUsername)){
			throw new NotPoundUserException();
		}
		String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
		User user = userRepository.findByUsername(changeUsername);
		user.changePassword(encodedPassword);
		userRepository.save(user);
	}

}
