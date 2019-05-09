package com.banking.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	private List<ApiError> subErrors;
	
	 ApiError(HttpStatus status) {
		super();
		this.status = status;
	}

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public List<ApiError> getSubErrors() {
		return subErrors;
	}

	public void setSubErrors(List<ApiError> subErrors) {
		this.subErrors = subErrors;
	}

	@Override
	public String toString() {
		return "ApiError [status=" + status + ", timestamp=" + timestamp + ", message=" + message + ", debugMessage="
				+ debugMessage + ", subErrors=" + subErrors + "]";
	}
	
}
