package com.example.demo.domain.user.common.exception;

import org.springframework.http.HttpStatus;

import com.example.demo.common.exception.Error;

public class NotPoundUriException extends UserException {
	public NotPoundUriException() {
		super(Error.NOT_FOUND_URI, HttpStatus.NOT_FOUND);
	}
}