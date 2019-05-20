package com.banking.exception;


public class CurrencyNotFoundException extends RuntimeException {

	public CurrencyNotFoundException()
	{
		super();
	}
	
	public CurrencyNotFoundException(String errorMessage)
	{
		super(errorMessage);
	}
	
	public CurrencyNotFoundException(String errorMessage,Throwable throwable)
	{
		super(errorMessage,throwable);
	}
	
	public CurrencyNotFoundException(Throwable throwable)
	{
		super(throwable);
	}
}
