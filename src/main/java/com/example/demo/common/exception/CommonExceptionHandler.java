package com.example.demo.common.exception;

import com.example.demo.common.model.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RequiredArgsConstructor
@RestControllerAdvice(basePackages = {"com.example.demo"})
public class CommonExceptionHandler {

	@ExceptionHandler(OrderException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<Void> ServerExceptionHandler(OrderException e) {

		Error error = e.getError();

		return Response.<Void>builder()
				.code(error.getCode())
				.message(error.getMessage())
				.build();
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response<Void> validExceptionHandler(MethodArgumentNotValidException e) {

		BindingResult bindingResult = e.getBindingResult();
		String errorMessage;

		try {
			errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
		} catch (NullPointerException exception) {
			errorMessage = Objects.requireNonNull(bindingResult.getGlobalError()).getDefaultMessage();
		}

		return Response.<Void>builder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message(String.valueOf(errorMessage))
				.build();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Response<Void> exceptionHandler(Exception e) {

		e.printStackTrace();

		return Response.<Void>builder()
				.code(Error.INTERNAL_SERVER_ERROR.getCode())
				.message(Error.INTERNAL_SERVER_ERROR.getMessage())
				.build();
	}

}
