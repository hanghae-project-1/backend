package com.example.demo.domain.user.common.exception;

import org.springframework.http.HttpStatus;
import com.example.demo.common.exception.Error;

public class UserWithdrawnException extends UserException {
	public UserWithdrawnException() {
		super(Error.USER_WITH_DRAWN, HttpStatus.NOT_FOUND);
	}
}