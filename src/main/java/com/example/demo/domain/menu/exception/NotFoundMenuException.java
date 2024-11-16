package com.example.demo.domain.menu.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundMenuException extends MenuException{

    public NotFoundMenuException() {
        super(Error.NOT_FOUND_MENU, HttpStatus.NOT_FOUND);
    }

}
