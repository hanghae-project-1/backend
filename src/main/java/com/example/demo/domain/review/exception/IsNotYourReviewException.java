package com.example.demo.domain.review.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class IsNotYourReviewException extends ReviewException {
	public IsNotYourReviewException() {
		super(Error.IS_NOT_YOUR_REVIEW, HttpStatus.BAD_REQUEST);
	}
}
