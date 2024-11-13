package com.example.demo.domain.category.exception;

import com.example.demo.common.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryMenuException extends RuntimeException{

    com.example.demo.common.exception.Error error;

    HttpStatus httpStatus;

    public CategoryMenuException(Error error, HttpStatus httpStatus) {
        this.error = error;
        this.httpStatus = httpStatus;
    }
}
