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
	private ChargesResponse chargesResponse;
	
	private String country;							
	private String loginsmeanscntry;
	private String debtcurr;
	private String paymentType;
	private Fxcharges fx1;
	private Normalcharges nm1;
	
	@Autowired
	PaymentchargesService(PaymentchargesRepository chargesrepository,
			Countrycurr countrycurr,Fxdata fxdata,Chargesdata chargesdata,ChargesResponse chargesResponse)
	{
		this.chargesrepository = chargesrepository;
		this.countrycurr = countrycurr;
		this.fxdata = fxdata;
		this.chargesResponse = chargesResponse;
	}
	
	public ResponseEntity<?> getPaymentCharges(ChargesRequestModel requestdata) throws CurrencyNotFoundException
	{
		country = requestdata.getLoginmeanscountry();
		loginsmeanscntry = requestdata.getLoginmeanscountry();
		if(country == null)
		{
			country = requestdata.getDebitoragent().getDracct().substring(0,2);
		}
		paymentType = chargesrepository.getPaymentType(requestdata.getDebitoragent().getDracct(), 
                requestdata.getCreditoracct().getCracct(),
                requestdata.getInstructedamount().getCurrency(),
                requestdata.getInstructedamount().getAmount());
		
		//Check if FX charges applicable or not
		debtcurr = requestdata.getDebitoragent().getDrcurr();
		if(debtcurr == null)
		{
			debtcurr = chargesrepository.getCurrencyOfCountry(requestdata.getDebitoragent().getDracct().substring(0,2));
		}
		
		if(debtcurr == null)
		{
			throw new CurrencyNotFoundException("no valid currency for country");
		}
		if(requestdata.getChargebearer() == "CRED")
	    {
	    	System.out.println("NO CHARGES APPLICABLE");
	    	return new ResponseEntity<ChargesRequestModel>(requestdata,HttpStatus.OK);
	    }
	    else
	    {
	    	if(debtcurr == requestdata.getInstructedamount().getCurrency())
			{
	        	nm1 = chargesrepository.getNormalCharges(requestdata,paymentType,country);
	    		System.out.println("NORMAL CHARGES APPLICABLE");
				System.out.println("NO FX CHARGES APPLICABLE");
			}
			else
			{
				nm1 = chargesrepository.getNormalCharges(requestdata,paymentType,country);
				System.out.println("NORMAL CHARGES APPLICABLE");
				if(country != "WB")
				{
					fx1 = chargesrepository.getFxCharges(requestdata,country);
				}
				 
			}
	    	List<Link> links = new ArrayList<>();
	    	Link lk = new Link(fx1.getLink(),"FX",fx1.getLanguage(),"text","GE foreign exchange");
	    	links.add(lk);
	    	List<String> ls = nm1.getLinks();
	    	for(String list : ls)
	    	{
	    		Link lk1 = new Link(list,"CHRG",nm1.getLanguage(),"text","GE charges");
	    		links.add(lk1);
	    	}
		    //Link lk = new Link(nm1.get,"CHRG",nm1.getLanguage(),"text","GE charges");
		    //links.add(lk);
		    chargesResponse.setLink(links);
			return new ResponseEntity<ChargesResponse>(chargesResponse,HttpStatus.OK);
	    }
		
	}
}
