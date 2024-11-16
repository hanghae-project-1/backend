package com.example.demo.domain.menu.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class DuplicateMenuException extends MenuException {

    public DuplicateMenuException() {
        super(Error.DUPLICATE_MENU_NAME, HttpStatus.BAD_REQUEST);
    }
}
