package com.example.demo.domain.user.common.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	public String getCurrentUsername() {
		UserDetails userDetails = getCurrentUser();
		return userDetails != null ? userDetails.getUsername() : null;
	}

	private UserDetails getCurrentUser() {
		Authentication authentication =
			SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		return (UserDetails)authentication.getPrincipal();
	}

}
