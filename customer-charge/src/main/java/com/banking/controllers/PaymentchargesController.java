package com.banking.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.banking.exception.CurrencyNotFoundException;
import com.banking.model.request.ChargesRequestModel;
import com.banking.services.PaymentchargesService;

@RestController
public class PaymentchargesController {

	 @Autowired
	 private PaymentchargesService paymentchargesService;


	@PostMapping("/payment")
	public ResponseEntity<?> getCharge(@Valid @RequestBody ChargesRequestModel requestdata) throws Exception
	{
	    System.out.println("sevanti" + requestdata.getCreditoracct());
	  	return paymentchargesService.getPaymentCharges(requestdata);
	  	
	    
    }

}
