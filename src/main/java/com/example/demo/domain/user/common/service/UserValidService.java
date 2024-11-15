package com.example.demo.domain.user.common.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.user.common.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidService {

	private final UserRepository userRepository;

	public boolean userNameCheck(String username) {
		return userRepository.existsByUsername(username);
	}

}
