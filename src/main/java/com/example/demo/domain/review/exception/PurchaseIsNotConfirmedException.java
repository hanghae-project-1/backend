package com.example.demo.domain.review.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class PurchaseIsNotConfirmedException extends ReviewException {
	public PurchaseIsNotConfirmedException() {
		super(Error.PURCHASE_IS_NOT_CONFIRMED, HttpStatus.BAD_REQUEST);
	}
}
