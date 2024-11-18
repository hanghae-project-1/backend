package com.example.demo.domain.user.common.service;

import com.example.demo.domain.user.common.dto.CustomUserDetails;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.NotFoundUserException;
import com.example.demo.domain.user.common.exception.UserWithdrawnException;
import com.example.demo.domain.user.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws
			UsernameNotFoundException {

		User user = userRepository.findByUsername(username);
		if (!user.getIsPublic()) {
			throw new UserWithdrawnException();
		}

		if (user != null) {
			return new CustomUserDetails(user);
		}

		throw new NotFoundUserException();
	}
}
