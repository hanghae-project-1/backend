package com.example.demo.domain.category.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class NotFoundCategoryMenuException extends CategoryMenuException {
    public NotFoundCategoryMenuException() {
        super(Error.NOT_FOUND_CATEGORYMENU, HttpStatus.NOT_FOUND);
    }
}
