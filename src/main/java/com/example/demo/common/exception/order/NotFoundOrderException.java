package com.example.demo.common.exception.order;

import com.example.demo.common.exception.Error;
import com.example.demo.common.exception.OrderException;
import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends OrderException {

	public NotFoundOrderException() {
		super(Error.NOT_FOUND_ORDER, HttpStatus.NOT_FOUND);
	}

}
