package com.example.demo.domain.order.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class ReturnPeriodPassedException extends OrderException {
	public ReturnPeriodPassedException() {
		super(Error.RETURN_PERIOD_PASSED, HttpStatus.BAD_REQUEST);
	}
}
