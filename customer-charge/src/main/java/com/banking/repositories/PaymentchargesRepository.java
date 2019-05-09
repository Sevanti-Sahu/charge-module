package com.banking.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.banking.Chargesdata;
import com.banking.Chargesdata.Normalcharges;
import com.banking.Countrycurr;
import com.banking.Countrycurr.Mappinglist;
import com.banking.Fxdata;
import com.banking.Fxdata.Fxcharges;
import com.banking.model.response.ChargesResponse.Link;
import com.banking.model.request.ChargesRequestModel;
import com.banking.model.response.ChargesResponse;

@Repository
public class PaymentchargesRepository {
	

	private Fxdata fxdata;
	
	private Chargesdata chargesdata;
	
	private Countrycurr countrycurr;
	private String language;
	
	private Map<String,Fxcharges> fxchargesmap ;
	
	
	@Autowired
	PaymentchargesRepository(Fxdata fxdata,Chargesdata chargesdata,Countrycurr countrycurr )
	{
		this.fxdata = fxdata;
		this.chargesdata = chargesdata;
		this.countrycurr = countrycurr;
	}
	
	
	@PostConstruct
	public void init()
	{
		this.fxchargesmap = preparefxmap(fxdata);
		System.out.println("pradeep");
	}
	
	
	public Map<String,Fxcharges> preparefxmap(Fxdata fxdata)
	{
		Map<String,Fxcharges> map1 = new HashMap<>();
		
		for(Fxcharges fx : fxdata.getFxcharges())
		{
			map1.put(fx.getCountry(),fx);
		}
		return map1;
	}
	
	
	//Method to get Normal charges details
	public Normalcharges getNormalCharges(ChargesRequestModel requestdata,String paymentType,String country)
	{
		language = mappingDefaultLanguageOfCountry(country);
		return getNormalChargesLinks(country,language,paymentType);
	}
	
	
	//Method to get FX charges details
	public Fxcharges getFxCharges(ChargesRequestModel requestdata,String country)
	{
		return getFxChargesLink(country);
	}
	
	
	//Method determining order type of transaction(POT API)
	public String getPaymentType(String dracct,String cracct,String curr,Double amount)
	{
		if((dracct.substring(0,2)) == (cracct.substring(0,2)))
		{
			if(curr == "EUR")
				return "INST" ;
			else
				return "DOMCT" ;
		}
		else
			return "TIP";
	}
	
	//Method for mapping country with its default language
	public String mappingDefaultLanguageOfCountry(String country)
	{
		List<Normalcharges> normalcharges = chargesdata.getNormalcharges();
		Map<String,String> map3 = new HashMap<>();
		for(Normalcharges nm : normalcharges)
		{
			if(nm.getDefaultLanguage() == true){
				map3.put(nm.getCountry(),nm.getLanguage());
			}
			
		}
		return map3.get(country);
	}
	
	 public Normalcharges getNormalChargesLinks(String country,String language,String orderType)
	{
		
		List<Normalcharges> normalcharges = chargesdata.getNormalcharges();
		Map<CountryLanguageOrderType,Normalcharges> map3 = new HashMap<>();
		for(Normalcharges nm : normalcharges)
		{
		
		map3.put(CountryLanguageOrderType.of(nm.getCountry(), nm.getLanguage(), nm.getOrderType()), nm);
		}
		System.out.println("SEVANTI");
		map3.forEach((k,v)->System.out.println("key: " + k + ", value: " + v));
		Normalcharges nm = new Normalcharges();
		nm = map3.get(CountryLanguageOrderType.of(country, language, orderType));
	   return nm;
	} 
	
	//Method for retrieving Fxdata
	public Fxcharges getFxChargesLink(String country)
	{
		/*List<Fxcharges> fxcharges = fxdata.getFxcharges();
		Map<String,Fxcharges> map1 = new HashMap<>();
		for(Fxcharges fx : fxcharges)
		{
			map1.put(fx.getCountry(),fx);
		}*/
		//return map1.get(country);
		return fxchargesmap.get(country);
		
	}
	
	
	//Method for retrieving currency of Country
	public String getCurrencyOfCountry(String cntry)
	{
	  List<Mappinglist> mappinglist = countrycurr.getMappinglist();
	  Map<String,String> map2 = new HashMap<>();
	  for(Mappinglist ml : mappinglist)
  	  {
		 map2.put(ml.getCountry(),ml.getCurrency());
	  }
	  return map2.get(cntry);
	}
	
	
	public static class CountryLanguageOrderType{
		private String country;
		private String language;
		private String orderType;
		public CountryLanguageOrderType(String country, String language, String orderType) {
			super();
			this.country = country;
			this.language = language;
			this.orderType = orderType;
		}
		
		@Override
		public String toString() {
			return "CountryLanguageOrderType [country=" + country + ", language=" + language + ", orderType="
					+ orderType + "]";
		}

		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((country == null) ? 0 : country.hashCode());
			result = prime * result + ((language == null) ? 0 : language.hashCode());
			result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CountryLanguageOrderType other = (CountryLanguageOrderType) obj;
			if (country == null) {
				if (other.country != null)
					return false;
			} else if (!country.equals(other.country))
				return false;
			if (language == null) {
				if (other.language != null)
					return false;
			} else if (!language.equals(other.language))
				return false;
			if (orderType == null) {
				if (other.orderType != null)
					return false;
			} else if (!orderType.equals(other.orderType))
				return false;
			return true;
		}

		public static CountryLanguageOrderType of(String country,String language,String orderType)
		{
			return new CountryLanguageOrderType(country,language,orderType);
		}
	}
}
