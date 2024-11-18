package com.example.demo.domain.user.common.service;

import com.example.demo.domain.user.common.dto.UserInfoRequestDto;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.NotFoundUserException;
import com.example.demo.domain.user.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PasswordChangeService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserService userService;

	public void changePassword(UserInfoRequestDto dto) {
		String currentUsername = userService.getCurrentUsername();
		String changeUsername = dto.getUsername();

		if (!currentUsername.equals(changeUsername)) {
			throw new NotFoundUserException();
		}
		String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
		User user = userRepository.findByUsername(changeUsername);
		user.changePassword(encodedPassword);
		userRepository.save(user);
	}

}
