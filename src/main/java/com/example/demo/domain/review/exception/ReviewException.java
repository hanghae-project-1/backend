package com.example.demo.domain.review.exception;

import com.example.demo.common.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewException extends RuntimeException {

	Error error;

	HttpStatus httpStatus;

	public ReviewException(Error error, HttpStatus httpStatus) {
		this.error = error;
		this.httpStatus = httpStatus;
	}

}
