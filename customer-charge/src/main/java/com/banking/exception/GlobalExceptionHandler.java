package com.banking.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.banking.model.request.ChargesRequestModel;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "special error")
	@ExceptionHandler(Exception.class)
	public void manageException(Exception e)
	{
		logger.error("exception handler excuted");
	}

    @ExceptionHandler(CurrencyNotFoundException.class)	
	public ResponseEntity<ApiError> handleCurrencyNotFound(CurrencyNotFoundException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(ex.getMessage());
		return new ResponseEntity<ApiError>(apiError,apiError.getStatus());
	}
}
