package com.example.demo.domain.order.exception;

import com.example.demo.common.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderException extends RuntimeException {

	Error error;
	HttpStatus httpStatus;

	public OrderException(Error error, HttpStatus httpStatus) {
		this.error = error;
		this.httpStatus = httpStatus;
	}

}