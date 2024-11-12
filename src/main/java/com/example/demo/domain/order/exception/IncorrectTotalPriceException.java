package com.example.demo.domain.order.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class IncorrectTotalPriceException extends OrderException {
	public IncorrectTotalPriceException() {
		super(Error.INCORRECT_TOTAL_PRICE, HttpStatus.BAD_REQUEST);
	}
}
