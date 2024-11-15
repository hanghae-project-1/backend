package com.example.demo.domain.user.common.exception;

import org.springframework.http.HttpStatus;
import com.example.demo.common.exception.Error;

public class DuplicateUsernameExistsException extends UserException {
	public DuplicateUsernameExistsException() {
		super(Error.DUPLICATE_USERNAME_EXISTS, HttpStatus.BAD_REQUEST);
	}
}