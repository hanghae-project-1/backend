package com.example.demo.domain.user.common.exception;

import org.springframework.http.HttpStatus;
import com.example.demo.common.exception.Error;

public class NotPoundUserException extends UserException {
	public NotPoundUserException() {
		super(Error.NOT_FOUND_USER, HttpStatus.NOT_FOUND);
	}
}