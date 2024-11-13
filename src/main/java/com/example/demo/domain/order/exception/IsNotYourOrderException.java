package com.example.demo.domain.order.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class IsNotYourOrderException extends OrderException {
	public IsNotYourOrderException() {
		super(Error.IS_NOT_YOUR_ORDER, HttpStatus.BAD_REQUEST);
	}
}
