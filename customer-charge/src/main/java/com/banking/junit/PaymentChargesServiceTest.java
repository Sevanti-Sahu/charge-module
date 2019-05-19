package com.banking.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
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
import com.banking.Chargesdata.Normalcharges;


@RunWith(MockitoJUnitRunner.class)
public class PaymentChargesServiceTest {
	
	@Mock
	PaymentchargesRepository paymentChargesRepository;
	
	ChargesRequestModel requestdata ;
	
	@Before
	public void before() throws IOException
	{	
	String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\request.json";
	ReadFileAsString read = new ReadFileAsString();
	String str = read.readFileAsString(path);
	ObjectMapper mapper = new ObjectMapper();
	requestdata = mapper.readValue(str, ChargesRequestModel.class);	
	}
	
	
	public void afterTest(String path,ResponseEntity<?> response) throws IOException
	{
		ReadFileAsString read = new ReadFileAsString();
		String str = read.readFileAsString(path);
		ObjectMapper mapper = new ObjectMapper();
		ChargesResponse expected = mapper.readValue(str, ChargesResponse.class);
		ResponseEntity<ChargesResponse> expectedresponse = new ResponseEntity<ChargesResponse>(expected,HttpStatus.OK);
		assertEquals(expectedresponse.toString(),response.toString());
	}
	
	@Test
	public void testServiceForFxCharges() throws IOException, CurrencyNotFoundException
	{
		PaymentchargesService paymentChargesService = new PaymentchargesService(paymentChargesRepository);
		Fxcharges fxcharge = new Fxcharges();
		fxcharge.setCountry("AT");
		fxcharge.setLanguage("En");
		fxcharge.setLink("https://myfx1.com");
		when(paymentChargesRepository.getFxCharges(Mockito.anyString())).thenReturn(fxcharge);
		ResponseEntity<?> response = paymentChargesService.getPaymentCharges(requestdata);
		String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\fx_response.json";
		afterTest(path,response);			
		
	}

	@Test
	public void testServiceForNormalCharges() throws IOException, CurrencyNotFoundException
	{
		PaymentchargesService paymentChargesService = new PaymentchargesService(paymentChargesRepository);
		requestdata.getInstructedamount().setCurrency("EUR");
		requestdata.setChargebearer("DEBT");
		Normalcharges normalcharges = new Normalcharges();
		normalcharges.setCountry("AT");
		normalcharges.setLanguage("En");
		normalcharges.setDefaultLanguage(true);
		normalcharges.setOrderType("TIP");
		List<String> links = new ArrayList<>();
		links.add("http://myapi1.com");
		links.add("http://myapi3.com");
		normalcharges.setLinks(links);
		when(paymentChargesRepository.getPaymentType(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn("TIP");
		when(paymentChargesRepository.getNormalCharges(Mockito.anyString(), Mockito.anyString())).thenReturn(normalcharges);
		ResponseEntity<?> response = paymentChargesService.getPaymentCharges(requestdata);
		String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\normalcharges_response.json";
		afterTest(path,response);	
	}
	
	@Test
	public void testServiceForBothCharges() throws IOException, CurrencyNotFoundException
	{
		PaymentchargesService paymentChargesService = new PaymentchargesService(paymentChargesRepository);
		requestdata.setChargebearer("DEBT");
		Fxcharges fxcharge = new Fxcharges();
		fxcharge.setCountry("AT");
		fxcharge.setLanguage("En");
		fxcharge.setLink("https://myfx1.com");
		when(paymentChargesRepository.getFxCharges(Mockito.anyString())).thenReturn(fxcharge);
		
		Normalcharges normalcharges = new Normalcharges();
		normalcharges.setCountry("AT");
		normalcharges.setLanguage("En");
		normalcharges.setDefaultLanguage(true);
		normalcharges.setOrderType("TIP");
		List<String> links = new ArrayList<>();
		links.add("http://myapi1.com");
		links.add("http://myapi3.com");
		normalcharges.setLinks(links);
		when(paymentChargesRepository.getPaymentType(Mockito.anyString(), Mockito.anyString(),Mockito.anyString())).thenReturn("TIP");
		when(paymentChargesRepository.getNormalCharges(Mockito.anyString(), Mockito.anyString())).thenReturn(normalcharges);
		
		ResponseEntity<?> response = paymentChargesService.getPaymentCharges(requestdata);
		String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\bothcharges_response.json";
		afterTest(path,response);	
	}
	
	@Test
	public void testServiceForNoCharges() throws IOException, CurrencyNotFoundException
	{
		PaymentchargesService paymentChargesService = new PaymentchargesService(paymentChargesRepository);
		requestdata.getInstructedamount().setCurrency("EUR");
		ResponseEntity<?> response = paymentChargesService.getPaymentCharges(requestdata);
		ChargesResponse chargesResponse = new ChargesResponse();
		ResponseEntity<ChargesResponse> expectedresponse = new ResponseEntity<ChargesResponse>(chargesResponse, HttpStatus.NO_CONTENT);
		//assertEquals(204,response.getStatusCodeValue());
		assertEquals(expectedresponse.toString(),response.toString());
		//assertNull(response.getBody());
	}
}
