package com.banking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.banking.Countrycurr;
import com.banking.Fxdata;
import com.banking.Fxdata.Fxcharges;
import com.banking.exception.CurrencyNotFoundException;
import com.banking.Chargesdata;
import com.banking.Chargesdata.Normalcharges;
import com.banking.model.request.ChargesRequestModel;
import com.banking.model.response.ChargesResponse;
import com.banking.model.response.ChargesResponse.Link;
import com.banking.repositories.PaymentchargesRepository;

@Service
public class PaymentchargesService {
	
	private PaymentchargesRepository paymentchargesrepository;
	
	@Autowired
	public PaymentchargesService(PaymentchargesRepository paymentchargesrepository)
	{
		this.paymentchargesrepository = paymentchargesrepository;
	
	}
	
	public ResponseEntity<?> getPaymentCharges(ChargesRequestModel requestdata) throws CurrencyNotFoundException
	{
		
		String country = requestdata.getLoginmeanscountry();
		String debtcurr = requestdata.getDebitoragent().getDrcurr();
	
		if(country == null)
		{
			country = requestdata.getDebitoragent().getDracct().substring(0,2);
		}
		
		if(debtcurr == null)
		{
			debtcurr = paymentchargesrepository.getCurrencyOfCountry(requestdata.getDebitoragent().getDracct().substring(0,2));
		}
		
		if(debtcurr == null)
		{
			throw new CurrencyNotFoundException("no valid currency for country");
		}
		
		
		Normalcharges normalcharges = getNormalCharges(requestdata,country);
		Fxcharges fxcharges = getFxCharges(requestdata,country,debtcurr);
		ChargesResponse chargesResponse = prepareResponse(normalcharges,fxcharges);
		if(chargesResponse.getLink().size() == 0)
		{
			return new ResponseEntity<ChargesResponse>(chargesResponse,HttpStatus.NO_CONTENT);
			
		}
		else
		{
			return new ResponseEntity<ChargesResponse>(chargesResponse,HttpStatus.OK);
		
		}
		
		
	    
   }
		
	    //Check if normal charges applicable or not and get it
		private Normalcharges getNormalCharges(ChargesRequestModel requestdata,String country)
		{
			Normalcharges normalmcharges = null;
			if((!(requestdata.getChargebearer()).equals("CRED"))){
			String paymentType = paymentchargesrepository.getPaymentType(requestdata.getDebitoragent().getDracct(), 
	                requestdata.getCreditoracct().getCracct(),
	                requestdata.getInstructedamount().getCurrency());

		    	System.out.println("NORMAL CHARGES APPLICABLE");
		    	normalmcharges = paymentchargesrepository.getNormalCharges(paymentType,country);
		    	
		    
			}
			return normalmcharges;
		}
		
		//check if FX charges applicable or not and get it
		private Fxcharges getFxCharges(ChargesRequestModel requestdata,String country,String debtcurr)
		{
			Fxcharges fxcharge;
			if((debtcurr).equals(requestdata.getInstructedamount().getCurrency()))
			{
				System.out.println("NO FX CHARGES APPLICABLE");
				fxcharge = null;
			}
			else
			{
				if(country != "WB")
				{
					fxcharge = paymentchargesrepository.getFxCharges(country);
					
				}
				else
				{
					System.out.println("NO FX CHARGES APPLICABLE");
					fxcharge = null;
				
				}		
				 
			}
		  return fxcharge;
	    }
		
		//preparing ressponse
		private ChargesResponse prepareResponse(Normalcharges normalcharges,Fxcharges fxcharges)
		{
			ChargesResponse chargesResponse = new ChargesResponse();
			List<Link> links = new ArrayList<>();
			
			if(fxcharges != null)
			{
				Link fxlink = new Link(fxcharges.getLink(),"FX",fxcharges.getLanguage(),"text","GE foreign exchange");
		    	links.add(fxlink);
		    	System.out.println("RESPONSE-FX CHARGES APPLICABLE");
			}
	    	if(normalcharges != null)
	    	{
	    		List<String> nmlink = normalcharges.getLinks();
		    	for(String list : nmlink)
		    	{
		    		Link lk1 = new Link(list,"CHRG",normalcharges.getLanguage(),"text","GE charges");
		    		links.add(lk1);
		    	}
		    	System.out.println("RESPONSE-Normal CHARGES APPLICABLE");
	    	}
	    	if(normalcharges != null || fxcharges != null)
	    	{
	    		chargesResponse.setLink(links);
	    		System.out.println("RESPONSE-CHARGES APPLICABLE");
	    	}
	    	
			return chargesResponse;
		}
}
