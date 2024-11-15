package com.example.demo.domain.store.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundStoreException extends StoreException{

    public NotFoundStoreException() {
        super(Error.NOT_FOUND_STORE, HttpStatus.NOT_FOUND);
    }
}
