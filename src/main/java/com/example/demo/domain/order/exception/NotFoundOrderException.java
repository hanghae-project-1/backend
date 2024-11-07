package com.example.demo.domain.order.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends OrderException {

	public NotFoundOrderException() {
		super(Error.NOT_FOUND_ORDER, HttpStatus.NOT_FOUND);
	}

}
