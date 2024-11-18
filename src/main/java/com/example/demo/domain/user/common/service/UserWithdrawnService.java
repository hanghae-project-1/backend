package com.example.demo.domain.user.common.service;

import com.example.demo.domain.user.common.dto.UserInfoRequestDto;
import com.example.demo.domain.user.common.entity.User;
import com.example.demo.domain.user.common.exception.NotFoundUserException;
import com.example.demo.domain.user.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class UserWithdrawnService {

	private final UserRepository userRepository;
	private final UserService userService;

	public void soft(UserInfoRequestDto dto) {
		String currentUsername = userService.getCurrentUsername();
		String changeUsername = dto.getUsername();

		if (!currentUsername.equals(changeUsername)) {
			throw new NotFoundUserException();
		}

		User user = userRepository.findByUsername(changeUsername);
		user.markAsDelete(currentUsername);
		userRepository.save(user);
	}

}
