package com.example.demo.domain.user.common.exception;

import com.example.demo.common.exception.Error;
import org.springframework.http.HttpStatus;

public class OwnerMismatchException extends UserException {

    public OwnerMismatchException() {
        super(Error.OWNER_MISMATCH, HttpStatus.FORBIDDEN);
    }
}
