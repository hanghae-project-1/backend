package com.example.demo.domain.user.common.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.common.dto.CustomUserDetails;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws
		UsernameNotFoundException {
		User userData = userRepository.findByUsername(username);

		if (userData != null) {
			return new CustomUserDetails(userData);
		}

		return null;
	}
}
