package com.example.demo.domain.store.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class IsNotYourStoreException extends StoreException {
	public IsNotYourStoreException() {
		super(Error.IS_NOT_YOUR_STORE, HttpStatus.BAD_REQUEST);
	}
}
