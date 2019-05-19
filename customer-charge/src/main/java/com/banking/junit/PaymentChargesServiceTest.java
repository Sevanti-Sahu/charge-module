package com.banking.junit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.banking.ReadFileAsString;
import com.banking.exception.CurrencyNotFoundException;
import com.banking.model.request.ChargesRequestModel;
import com.banking.model.response.ChargesResponse;
import com.banking.repositories.PaymentchargesRepository;
import com.banking.services.PaymentchargesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.banking.Fxdata.Fxcharges;

@RunWith(MockitoJUnitRunner.class)
public class PaymentChargesServiceTest {
	
	@Mock
	PaymentchargesRepository paymentChargesRepository;
	
	
	
	@Test
	public void testService() throws IOException, CurrencyNotFoundException
	{
		PaymentchargesService paymentChargesService = new PaymentchargesService(paymentChargesRepository);
		ChargesRequestModel requestdata ;
//		
		String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\request.json";
		ReadFileAsString read = new ReadFileAsString();
		String str = new String();
		str = read.readFileAsString(path);
		ObjectMapper mapper = new ObjectMapper();
		requestdata = mapper.readValue(str, ChargesRequestModel.class);	
		Fxcharges fxcharge = new Fxcharges();
		fxcharge.setCountry("AT");
		fxcharge.setLanguage("En");
		fxcharge.setLink("https://myfx1.com");
		when(paymentChargesRepository.getFxCharges(Mockito.anyString())).thenReturn(fxcharge);
		ResponseEntity<?> response = paymentChargesService.getPaymentCharges(requestdata);
		path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\fx_response.json";
		str = read.readFileAsString(path);
		ChargesResponse expected = mapper.readValue(str, ChargesResponse.class);
		ResponseEntity<ChargesResponse> expectedresponse = new ResponseEntity<ChargesResponse>(expected,HttpStatus.OK);
		assertEquals(expectedresponse.toString(),response.toString());
//		paymentchargesrepository.getFxCharges(country
		
		
		
		
		
	}

}
