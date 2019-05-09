package com.banking.exception;


public class CurrencyNotFoundException extends Exception {

	
	public CurrencyNotFoundException(String errorMessage)
	{
		super(errorMessage);
	}
}
