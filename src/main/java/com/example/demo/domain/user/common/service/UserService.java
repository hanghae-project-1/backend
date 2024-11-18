package com.example.demo.domain.user.common.service;

import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.NotFoundUserException;
import com.example.demo.domain.user.common.exception.UserWithdrawnException;
import com.example.demo.domain.user.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	public String getCurrentUsername() {
		UserDetails userDetails = getCurrentUser();

		if (userDetails == null) {
			throw new NotFoundUserException();
		}

		User user = userRepository.findByUsername(userDetails.getUsername());
		if (!user.getIsPublic()) {
			throw new UserWithdrawnException();
		}

		return userDetails.getUsername();
	}


	public String getCurrentUserRole() {
		return userRepository.findByUsername(getCurrentUsername()).getRole().getKey();
	}

	private UserDetails getCurrentUser() {
		Authentication authentication =
				SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new NotFoundUserException();
		}
		return (UserDetails) authentication.getPrincipal();
	}

}
