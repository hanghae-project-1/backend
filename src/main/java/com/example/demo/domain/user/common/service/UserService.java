package com.example.demo.domain.user.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.common.exception.NotPoundUserException;
import com.example.demo.domain.user.common.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	public String getCurrentUsername() {
		UserDetails userDetails = getCurrentUser();
		return userDetails != null ? userDetails.getUsername() : null;
	}

	public String getCurrentUserRole(){
		return userRepository.findByUsername(getCurrentUsername()).getRole().getKey();
	}

	private UserDetails getCurrentUser() {
		Authentication authentication =
			SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new NotPoundUserException();
		}
		return (UserDetails)authentication.getPrincipal();
	}

}
