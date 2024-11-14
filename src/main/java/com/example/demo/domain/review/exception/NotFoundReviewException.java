package com.example.demo.domain.review.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundReviewException extends ReviewException {
	public NotFoundReviewException() {
		super(Error.NOT_FOUND_REVIEW, HttpStatus.NOT_FOUND);
	}
}
