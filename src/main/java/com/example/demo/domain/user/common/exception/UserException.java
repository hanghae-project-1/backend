package com.example.demo.domain.user.common.exception;

import org.springframework.http.HttpStatus;
import com.example.demo.common.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserException extends RuntimeException {

	Error error;
	HttpStatus httpStatus;
	public UserException(Error error, HttpStatus httpStatus) {
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
