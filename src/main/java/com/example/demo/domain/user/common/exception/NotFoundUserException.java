package com.example.demo.domain.user.common.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundUserException extends UserException {
	public NotFoundUserException() {
		super(Error.NOT_FOUND_USER, HttpStatus.NOT_FOUND);
	}
}