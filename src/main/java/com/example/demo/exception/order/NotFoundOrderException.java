package com.example.demo.exception.order;

import com.example.demo.exception.Error;
import com.example.demo.exception.OrderException;
import org.springframework.http.HttpStatus;

public class NotFoundOrderException extends OrderException {

	public NotFoundOrderException() {
		super(Error.NOT_FOUND_ORDER, HttpStatus.NOT_FOUND);
	}

}
