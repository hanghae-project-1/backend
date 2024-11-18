package com.example.demo.domain.ai.exception;

import com.example.demo.common.exception.Error;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotFoundAiException extends AiException{

    public NotFoundAiException() {
        super(Error.NOT_FOUND_AI, HttpStatus.NOT_FOUND);
    }
}
