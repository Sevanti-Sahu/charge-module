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
	
	private PaymentchargesRepository chargesrepository;
	private Countrycurr countrycurr;
	private Fxdata fxdata;
	private Chargesdata chargesdata;
	private ChargesResponse chargesResponse = new ChargesResponse();
								
	private Fxcharges fxcharges;
	private Normalcharges normalcharges;
	
	@Autowired
	PaymentchargesService(PaymentchargesRepository chargesrepository,
			Countrycurr countrycurr,Fxdata fxdata,Chargesdata chargesdata)
	{
		this.chargesrepository = chargesrepository;
		this.countrycurr = countrycurr;
		this.fxdata = fxdata;
		this.chargesdata = chargesdata;
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
			debtcurr = chargesrepository.getCurrencyOfCountry(requestdata.getDebitoragent().getDracct().substring(0,2));
		}
		
		/*if(debtcurr == null)
		{
			throw new CurrencyNotFoundException("no valid currency for country");
		}*/
		
		
		String paymentType = chargesrepository.getPaymentType(requestdata.getDebitoragent().getDracct(), 
                requestdata.getCreditoracct().getCracct(),
                requestdata.getInstructedamount().getCurrency());
		
		//Check if FX charges applicable or not
		
		if((requestdata.getChargebearer()).equals("CRED"))
	    {
	    	System.out.println("NO CHARGES APPLICABLE");
	    	return new ResponseEntity<ChargesRequestModel>(requestdata,HttpStatus.OK);
	    }
	    else
	    {
	    	if((debtcurr).equals(requestdata.getInstructedamount().getCurrency()))
			{
	        	normalcharges = chargesrepository.getNormalCharges(paymentType,country);
	    		System.out.println("NORMAL CHARGES APPLICABLE");
				System.out.println("NO FX CHARGES APPLICABLE");
			}
			else
			{
				normalcharges = chargesrepository.getNormalCharges(paymentType,country);
				System.out.println("NORMAL CHARGES APPLICABLE");
				if(country != "WB")
				{
					fxcharges = chargesrepository.getFxCharges(country);
					
				}
				 
			}
	    	List<Link> links = new ArrayList<>();
	    	/*Link fxlink = new Link(fxcharges.getLink(),"FX",fxcharges.getLanguage(),"text","GE foreign exchange");
	    	links.add(fxlink);*/
	    	List<String> nmlink = normalcharges.getLinks();
	    	for(String list : nmlink)
	    	{
	    		Link lk1 = new Link(list,"CHRG",normalcharges.getLanguage(),"text","GE charges");
	    		links.add(lk1);
	    	}
		    
		    chargesResponse.setLink(links);
			return new ResponseEntity<ChargesResponse>(chargesResponse,HttpStatus.OK);
	    }
		
	}
}
