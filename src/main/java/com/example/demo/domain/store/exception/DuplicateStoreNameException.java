package com.example.demo.domain.store.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class DuplicateStoreNameException extends StoreException {

    public DuplicateStoreNameException() {
        super(Error.DUPLICATE_STORE_NAME, HttpStatus.BAD_REQUEST);
    }
}
