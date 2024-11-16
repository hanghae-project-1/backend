package com.example.demo.domain.menu.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundMenuAndStoreException extends MenuException{

    public NotFoundMenuAndStoreException() {
        super(Error.NOT_FOUND_MENU_STORE, HttpStatus.NOT_FOUND);
    }
}
