package com.example.demo.domain.category.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class DuplicateCategoryMenuException extends CategoryMenuException{

    public DuplicateCategoryMenuException() {
        super(Error.DUPLICATE_CATEGORYMENU_NAME, HttpStatus.BAD_REQUEST);
    }
}
